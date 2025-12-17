package com.arttrip.android.presentation.exhibition

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.exhibition.GetExhibitionDetailUseCase
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailEffect
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailIntent
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExhibitionDetailViewModel
    @Inject
    constructor(
        private val getExhibitionDetailUseCase: GetExhibitionDetailUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(ExhibitionDetailState())
        val state: StateFlow<ExhibitionDetailState> = _state

        private val _effect = MutableSharedFlow<ExhibitionDetailEffect>()
        val effect: SharedFlow<ExhibitionDetailEffect> = _effect

        private var inFlightJob: Job? = null
        private var pendingTarget: Boolean? = null

        private val debugLog = true
        private var reqSeq = 0L

        private fun log(msg: String) {
            if (debugLog) Log.d("ExhibitionBookmark", msg)
        }

        fun onIntent(intent: ExhibitionDetailIntent) {
            when (intent) {
                is ExhibitionDetailIntent.Initialize -> {
                    loadExhibitionDetail(intent.exhibitId)
                }

                is ExhibitionDetailIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(ExhibitionDetailEffect.NavigateBack) }
                }

                is ExhibitionDetailIntent.BookmarkClicked -> {
                    val exhibitId = _state.value.detail?.exhibitId ?: return

                    val before = _state.value.isBookmarked
                    val target = !before

                    // 1) optimistic
                    _state.update { it.copy(isBookmarked = target) }
                    log("[UI] tap exhibitId=$exhibitId $before -> $target")

                    // 2) serialize request
                    requestBookmarkChange(exhibitId = exhibitId, target = target)
                }
            }
        }

        private fun loadExhibitionDetail(exhibitId: Int) {
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

        private fun requestBookmarkChange(
            exhibitId: Long,
            target: Boolean,
        ) {
            if (inFlightJob?.isActive == true) {
                val prev = pendingTarget
                pendingTarget = target
                if (prev != target) {
                    log("[Q] pending ${prev ?: "null"} -> $target")
                }
                return
            }

            val seq = ++reqSeq
            val uiAtRequestStart = _state.value.isBookmarked

            inFlightJob =
                viewModelScope.launch {
                    _state.update { it.copy(isBookmarkSyncing = true) }
                    log("[REQ#$seq] start target=$target")

                    val result =
                        runCatching {
                            // 실제 API로 변경
                            //  syncBookmarkMockUseCase(exhibitId, target)
                        }

                    _state.update { it.copy(isBookmarkSyncing = false) }

                    if (result.isSuccess) {
                        log("[REQ#$seq] ok target=$target")
                    } else {
                        val err = result.exceptionOrNull()?.message
                        log("[REQ#$seq] fail target=$target err=$err")

                        val currentUi = _state.value.isBookmarked
                        if (currentUi == uiAtRequestStart && currentUi == target) {
                            _state.update { it.copy(isBookmarked = !target) }
                            log("[REQ#$seq] rollback -> ${_state.value.isBookmarked}")
                        } else {
                            log("[REQ#$seq] rollback skipped (UI changed during request)")
                        }
                    }

                    val next = pendingTarget
                    pendingTarget = null

                    if (next != null && next != target) {
                        log("[NEXT] pending=$next (after target=$target) -> send")
                        requestBookmarkChange(exhibitId = exhibitId, target = next)
                    } else if (next != null) {
                        log("[NEXT] pending=$next equals last target=$target -> skip")
                    }
                }
        }
    }
