package com.arttrip.app.core.util.bookmark

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ### 앱 전역 전시 북마크 상태 저장소
 *
 * - 여러 화면에서 동일 exhibitId의 북마크 UI를 일관되게 유지
 * - 연타 시 서버 요청을 id별로 debounce 하며, 실패 시 필요한 경우에만 롤백
 *
 * ---
 * ### 기본 사용 패턴
 *
 * - **원격 응답으로 초기값 주입**
 *    - 상세/단건: [setFromRemote]
 *    - 리스트: [upsertFromRemote]
 *
 *   주의: 리컴포즈마다 주입하지 말고, **API 성공 시점** 또는 Paging Row의 `LaunchedEffect`로 1회 주입
 *
 * - **사용자 토글(하트 클릭)**
 *    - [toggle]
 *
 * - **UI 구독**
 *    - 북마크: [bookmarkedFlow]
 *    - 동기화 중 여부: [isSyncingFlow]
 *
 * ---
 * ### MVI State에 반영하고 싶을 때 (VM 예시)
 *
 * ```kotlin
 * init {
 *     viewModelScope.launch {
 *         state
 *             .map { it.detail?.exhibitId }
 *             .distinctUntilChanged()
 *             .filterNotNull()
 *             .collectLatest { id ->
 *                 bookmarkStore.bookmarkedFlow(id)
 *                     .collectLatest { bookmarked ->
 *                         _state.update { it.copy(isBookmarked = bookmarked) }
 *                     }
 *             }
 *     }
 * }
 * ```
 */

@Singleton
class BookmarkStore
    @Inject
    constructor(
        private val syncer: ExhibitionBookmarkSyncer,
    ) {
        private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

        private val _bookmarked = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
        val bookmarked: StateFlow<Map<Int, Boolean>> = _bookmarked.asStateFlow()

        private val _syncing = MutableStateFlow<Set<Int>>(emptySet())
        val syncing: StateFlow<Set<Int>> = _syncing.asStateFlow()

        private val dirty = mutableSetOf<Int>()
        private val pendingJobs = mutableMapOf<Int, Job>()
        private val lastSentTarget = mutableMapOf<Int, Boolean>()

        /**
         * 특정 exhibitId의 북마크 상태를 Boolean Flow로 제공한다.
         * - 기본값: store에 없으면 false
         * - UI/VM 어디서든 구독 가능
         */
        fun bookmarkedFlow(exhibitId: Int): Flow<Boolean> = bookmarked.map { it[exhibitId] ?: false }.distinctUntilChanged()

        /**
         * 여러 exhibitId의 북마크 상태를 Map 형태로 제공
         *
         * - ids 변경 시 기존 구독 자동 취소
         * - bookmarkedFlow(id)를 id별로 개별 구독
         */
        fun bookmarkedByIdsFlow(exhibitIdsFlow: Flow<List<Int>>): Flow<Map<Int, Boolean>> =
            channelFlow {
                var currentMap = emptyMap<Int, Boolean>()

                exhibitIdsFlow
                    .map { ids -> ids.distinct().sorted() }
                    .distinctUntilChanged()
                    .collectLatest { ids ->
                        // ids가 바뀌면 이 블록 전체 취소됨
                        currentMap = currentMap.filterKeys { it in ids }
                        send(currentMap)

                        ids.forEach { id ->
                            launch {
                                bookmarkedFlow(id).collectLatest { bookmarked ->
                                    currentMap = currentMap + (id to bookmarked)
                                    send(currentMap)
                                }
                            }
                        }
                    }
            }

        fun isSyncingFlow(exhibitId: Int): Flow<Boolean> = syncing.map { it.contains(exhibitId) }.distinctUntilChanged()

        /**
         * 원격(서버) 응답으로 들어온 단건 북마크 값을 store에 주입
         * - dirty 상태(사용자 토글로 로컬 우선)인 id는 덮어쓰지 않는다.
         * - 동일 값이면 no-op
         *
         * 사용처: 전시 상세 조회 성공 시점, Paging Row의 LaunchedEffect(단건 주입) 등
         */
        fun setFromRemote(
            exhibitId: Int,
            value: Boolean,
        ) {
            if (dirty.contains(exhibitId)) return

            val cur = _bookmarked.value[exhibitId]
            if (cur == value) return

            _bookmarked.update { it + (exhibitId to value) }

            lastSentTarget[exhibitId] = value
        }

        /**
         * 원격(서버) 응답으로 들어온 여러 건의 북마크 값을 store에 일괄 주입한
         * - dirty 상태인 id는 덮어쓰지 않음
         *
         * 사용처: 홈/검색/추천 등 리스트 API 성공 시점
         * ```kotlin
         * bookmarkStore.upsertFromRemote(list.associate { it.exhibitId to it.isBookmarked })
         * ```
         */
        fun upsertFromRemote(pairs: Map<Int, Boolean>) {
            if (pairs.isEmpty()) return

            _bookmarked.update { old ->
                var next = old
                for ((id, v) in pairs) {
                    if (dirty.contains(id)) continue
                    if (next[id] == v) continue
                    next = next + (id to v)
                    lastSentTarget[id] = v
                }
                next
            }
        }

        /**
         * 현재 store의 상태를 기준으로 북마크 값을 토글
         * - optimistic 업데이트 후, id별 debounce로 서버 동기화가 수행.
         */
        fun toggle(exhibitId: Int) {
            val before = _bookmarked.value[exhibitId] ?: false
            setTarget(exhibitId, !before)
        }

        /**
         * 특정 target(true/false)로 즉시 반영(optimistic)한 뒤, id별 debounce로 서버와 동기화
         * - 외부에서 명시적으로 target을 지정하고 싶을 때 사용
         */
        fun setTarget(
            exhibitId: Int,
            target: Boolean,
        ) {
            dirty.add(exhibitId)

            _bookmarked.update { it + (exhibitId to target) }

            // id별 debounce
            pendingJobs[exhibitId]?.cancel()
            pendingJobs[exhibitId] =
                scope.launch {
                    delay(DEBOUNCE_MS)
                    sync(exhibitId, target)
                }
        }

        private suspend fun sync(
            exhibitId: Int,
            target: Boolean,
        ) {
            val current = _bookmarked.value[exhibitId] ?: false
            if (current != target) return // UI가 이미 다른 상태면 이 sync는 무시
            if (lastSentTarget[exhibitId] == target) return

            _syncing.update { it + exhibitId }

            val ok =
                runCatching { syncer.sync(exhibitId, target) }
                    .getOrElse { false }

            _syncing.update { it - exhibitId }

            if (ok) {
                lastSentTarget[exhibitId] = target
                dirty.remove(exhibitId)
            } else {
                // 실패 시: 현재 UI가 아직 target이면 롤백
                val current = _bookmarked.value[exhibitId] ?: false
                if (current == target) {
                    _bookmarked.update { it + (exhibitId to !target) }
                }
            }
        }

        private companion object {
            private const val DEBOUNCE_MS = 250L
        }
    }
