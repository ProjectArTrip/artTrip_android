package com.arttrip.app.core.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * 스크롤 방향에 따라 UI 표시 여부를 결정하는 상태값을 반환
 *
 * ### 동작 규칙
 * - 리스트를 **아래로 스크롤(콘텐츠를 위로 밀어올림)** 하면 `visible = false`
 * - 리스트를 **위로 스크롤(콘텐츠를 아래로 내림)** 하면 `visible = true`
 * - 리스트가 **최상단 근처**(첫 아이템 + 작은 오프셋)이면 항상 `visible = true` 로 유지합니다.
 *
 * ### 사용 예시
 * ```kotlin
 * val listState = rememberLazyListState()
 * val headerVisible = rememberScrollUpVisible(listState).value
 *
 * AnimatedVisibility(
 *   visible = headerVisible,
 *   enter = expandVertically() + fadeIn(),
 *   exit = shrinkVertically() + fadeOut(),
 * ) {
 *   // 스크롤 시 숨김/표시될 헤더 UI
 * }
 * ```
 *
 * ### 파라미터 설명
 * @param listState 스크롤 방향을 관찰할 [LazyListState]
 * @param topOffsetThreshold 최상단 판정 임계값(픽셀 단위에 가까운 오프셋).
 * `firstVisibleItemIndex == 0` 이고 `firstVisibleItemScrollOffset <= topOffsetThreshold` 인 경우 항상 표시합니다.
 * @param minDeltaToToggle 미세한 스크롤 흔들림으로 인한 깜빡임 방지를 위한 최소 이동량.
 * 동일 index에서 scrollOffset 변화량이 이 값보다 클 때만 방향 토글로 인식합니다.
 *
 * @return 스크롤 상태에 따라 갱신되는 visible 상태([MutableState])
 */
@Composable
fun rememberScrollUpVisible(
    listState: LazyListState,
    topOffsetThreshold: Int = 8,
    minDeltaToToggle: Int = 6,
): MutableState<Boolean> {
    val visible = remember { mutableStateOf(true) }

    LaunchedEffect(listState) {
        var lastIndex = listState.firstVisibleItemIndex
        var lastOffset = listState.firstVisibleItemScrollOffset

        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .distinctUntilChanged()
            .collectLatest { (index, offset) ->
                // 최상단 근처에서는 항상 보이게(UX 안정)
                if (index == 0 && offset <= topOffsetThreshold) {
                    visible.value = true
                } else {
                    val delta = offset - lastOffset

                    val scrollingDown =
                        (index > lastIndex) || (index == lastIndex && delta > minDeltaToToggle)
                    val scrollingUp =
                        (index < lastIndex) || (index == lastIndex && delta < -minDeltaToToggle)

                    if (scrollingDown) {
                        visible.value = false
                    } else if (scrollingUp) {
                        visible.value = true
                    }
                }

                lastIndex = index
                lastOffset = offset
            }
    }

    return visible
}
