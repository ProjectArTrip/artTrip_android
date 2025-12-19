package com.arttrip.android.presentation.exhibition

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.bookmark.AddBookmarkUseCase
import com.arttrip.android.domain.usecase.bookmark.CheckBookmarkUseCase
import com.arttrip.android.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.arttrip.android.domain.usecase.exhibition.GetExhibitionDetailUseCase
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailEffect
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailIntent
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ExhibitionDetailViewModel
    @Inject
    constructor(
        private val getExhibitionDetailUseCase: GetExhibitionDetailUseCase,
        private val checkBookmarkUseCase: CheckBookmarkUseCase,
        private val addBookmarkUseCase: AddBookmarkUseCase,
        private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(ExhibitionDetailState())
        val state: StateFlow<ExhibitionDetailState> = _state

        private val _effect = MutableSharedFlow<ExhibitionDetailEffect>()
        val effect: SharedFlow<ExhibitionDetailEffect> = _effect

        private companion object {
            private const val TAG = "ExhibitionBookmark"
            private const val BOOKMARK_DEBOUNCE_MS = 250L
        }

        private fun log(msg: String) {
            Log.d(TAG, msg)
        }

        private val bookmarkTargetFlow =
            MutableSharedFlow<Boolean>(
                replay = 0,
                extraBufferCapacity = 1,
                onBufferOverflow = BufferOverflow.DROP_OLDEST,
            )

        private var userTouchedBookmark = false
        private var lastSentTarget: Boolean? = null

        init {
            viewModelScope.launch {
                bookmarkTargetFlow
                    .debounce(250)
                    .collectLatest { target ->
                        syncBookmark(target)
                    }
            }
        }

        fun onIntent(intent: ExhibitionDetailIntent) {
            when (intent) {
                is ExhibitionDetailIntent.Initialize -> {
                    initialize(intent.exhibitId)
                }

                is ExhibitionDetailIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(ExhibitionDetailEffect.NavigateBack) }
                }

                is ExhibitionDetailIntent.BookmarkClicked -> {
                    val exhibitId = _state.value.detail?.exhibitId ?: return

                    val before = _state.value.isBookmarked
                    val target = !before

                    userTouchedBookmark = true

                    _state.update { it.copy(isBookmarked = target) }
                    log("[UI] tap exhibitId=$exhibitId $before -> $target")

                    bookmarkTargetFlow.tryEmit(target)
                }
            }
        }

        private fun initialize(exhibitId: Int) {
            userTouchedBookmark = false
            fetchExhibitionDetail(exhibitId)
            fetchBookmarkStatus(exhibitId)
        }

        private fun fetchExhibitionDetail(exhibitId: Int) {
            viewModelScope.launch {
                getExhibitionDetailUseCase(exhibitId).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _state.update { it.copy(isLoading = true, errorMessage = null) }
                        }

                        is ApiResult.Success -> {
                            _state.update {
                                it.copy(
                                    detail = result.data,
                                    isLoading = false,
                                    errorMessage = null,
                                )
                            }
                        }

                        is ApiResult.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "전시 상세를 가져오는데 실패했습니다.",
                                )
                            }
                        }
                    }
                }
            }
        }

        private fun fetchBookmarkStatus(exhibitId: Int) {
            viewModelScope.launch {
                checkBookmarkUseCase(exhibitId).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                        }

                        is ApiResult.Success -> {
                            _state.update { s ->
                                val serverValue = result.data.isBookmarked // (도메인 정리했으면 isBookmarked)
                                if (userTouchedBookmark || s.isBookmarkSyncing) {
                                    // 사용자가 이미 만졌으면 서버값으로 isBookmarked 덮지 않음
                                    s
                                } else {
                                    s.copy(isBookmarked = serverValue)
                                }
                            }
                        }

                        is ApiResult.Error -> {
                            _state.update {
                                it.copy(
                                    errorMessage = "즐겨찾기 정보를 가져오는데 실패했습니다.",
                                )
                            }
                        }
                    }
                }
            }
        }

        private suspend fun syncBookmark(target: Boolean) {
            val exhibitId = _state.value.detail?.exhibitId ?: return

            if (lastSentTarget == target) {
                log("[SYNC] skip duplicate target=$target")
                return
            }

            _state.update { it.copy(isBookmarkSyncing = true) }
            log("[SYNC] start target=$target")

            val result =
                runCatching {
                    if (target) {
                        addBookmarkUseCase(exhibitId).first { it !is ApiResult.Loading }
                    } else {
                        removeBookmarkUseCase(exhibitId).first { it !is ApiResult.Loading }
                    }
                }.getOrElse { t ->
                    log("[SYNC] exception: ${t.message}")
                }

            _state.update { it.copy(isBookmarkSyncing = false) }

            when (result) {
                is ApiResult.Success<*> -> {
                    log("[SYNC] ok target=$target")
                    lastSentTarget = target
                }

                is ApiResult.Error -> {
                    log("[SYNC] fail target=$target err=${result.error}")

                    // 실패 시: "현재 UI가 아직 target이면" 롤백
                    val current = _state.value.isBookmarked
                    if (current == target) {
                        _state.update { it.copy(isBookmarked = !target) }
                        log("[SYNC] rollback -> ${_state.value.isBookmarked}")
                    } else {
                        log("[SYNC] rollback skipped (UI already changed)")
                    }
                }

                is ApiResult.Loading -> Unit
            }
        }
    }
