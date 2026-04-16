package com.arttrip.android.presentation.reviewwrite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.core.ui.UiMessage
import com.arttrip.android.core.util.copyToCacheFile
import com.arttrip.android.domain.model.network.ApiError
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.review.CreateReviewUseCase
import com.arttrip.android.domain.usecase.review.EditReviewUseCase
import com.arttrip.android.domain.usecase.review.GetReviewDetailUseCase
import com.arttrip.android.presentation.reviewwrite.contract.MAX_REVIEW_PHOTO_COUNT
import com.arttrip.android.presentation.reviewwrite.contract.MAX_REVIEW_TEXT_LENGTH
import com.arttrip.android.presentation.reviewwrite.contract.MIN_REVIEW_TEXT_LENGTH
import com.arttrip.android.presentation.reviewwrite.contract.ReviewModeUi
import com.arttrip.android.presentation.reviewwrite.contract.ReviewPhotoItem
import com.arttrip.android.presentation.reviewwrite.contract.ReviewWriteEffect
import com.arttrip.android.presentation.reviewwrite.contract.ReviewWriteIntent
import com.arttrip.android.presentation.reviewwrite.contract.ReviewWriteState
import com.arttrip.android.presentation.reviewwrite.model.ReviewWriteMode
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class ReviewWriteViewModel
    @Inject
    constructor(
        @param:ApplicationContext private val appContext: Context,
        private val getReviewDetailUseCase: GetReviewDetailUseCase,
        private val createReviewUseCase: CreateReviewUseCase,
        private val editReviewUseCase: EditReviewUseCase,
    ) : ViewModel() {
        private val _state =
            MutableStateFlow(ReviewWriteState())
        val state: StateFlow<ReviewWriteState> = _state

        private val _effect = MutableSharedFlow<ReviewWriteEffect>()
        val effect: SharedFlow<ReviewWriteEffect> = _effect

        fun onIntent(intent: ReviewWriteIntent) {
            when (intent) {
                is ReviewWriteIntent.Initialize -> {
                    handleInitialize(intent.mode)
                }

                ReviewWriteIntent.BackClicked -> {
                    _state.update { it.copy(isExitConfirmDialogVisible = true) }
                }

                ReviewWriteIntent.VisitDateClicked,
                -> {
                    _state.update { s ->
                        val baseDate = s.visitDate
                        s.copy(
                            isVisitDateSheetVisible = true,
                            calendarMonth = baseDate?.let { YearMonth.from(it) } ?: YearMonth.now(),
                        )
                    }
                }
                ReviewWriteIntent.VisitDateSheetDismissed -> {
                    _state.update { it.copy(isVisitDateSheetVisible = false) }
                }
                is ReviewWriteIntent.VisitDateSelected -> {
                    _state.update {
                        it.copy(
                            visitDate = intent.date,
                            isVisitDateSheetVisible = false, // TODO 선택 즉시 닫기(임시)
                            calendarMonth = YearMonth.from(intent.date),
                        )
                    }
                }

                is ReviewWriteIntent.CalendarMonthChanged -> {
                    _state.update { it.copy(calendarMonth = intent.month) }
                }

                ReviewWriteIntent.AddPhotoClicked -> {
                    val s = _state.value
                    if (!s.canAddPhoto) return
                    viewModelScope.launch {
                        _effect.emit(ReviewWriteEffect.LaunchPhotoPicker)
                    }
                }

                is ReviewWriteIntent.PhotoPickerResult -> {
                    _state.update { s ->
                        val incoming =
                            intent.uris.map { uri ->
                                ReviewPhotoItem.Local(uri)
                            }

                        s.copy(
                            photos = mergePhotos(s.photos, incoming),
                        )
                    }
                }

                is ReviewWriteIntent.RemovePhotoClicked -> {
                    _state.update { s ->
                        val target = s.photos.getOrNull(intent.index) ?: return@update s

                        when (target) {
                            is ReviewPhotoItem.Remote -> {
                                s.copy(
                                    photos = s.photos.filterIndexed { index, _ -> index != intent.index },
                                    deletedImageIds = s.deletedImageIds + target.imageId,
                                )
                            }

                            is ReviewPhotoItem.Local -> {
                                s.copy(
                                    photos = s.photos.filterIndexed { index, _ -> index != intent.index },
                                )
                            }
                        }
                    }
                }

                is ReviewWriteIntent.ReviewTextChanged -> {
                    _state.update { s ->
                        val newText = intent.text.take(MAX_REVIEW_TEXT_LENGTH)
                        s.copy(
                            reviewText = newText,
                            showReviewLengthError =
                                s.showReviewLengthError &&
                                    newText.length < MIN_REVIEW_TEXT_LENGTH,
                        )
                    }
                }
                ReviewWriteIntent.SubmitClicked -> {
                    submit()
                }
                ReviewWriteIntent.ExitConfirmClicked -> {
                    viewModelScope.launch {
                        _state.update { it.copy(isExitConfirmDialogVisible = false) }
                        _effect.emit(ReviewWriteEffect.NavigateBack)
                    }
                }
                ReviewWriteIntent.ExitConfirmDialogDismissed -> {
                    _state.update { it.copy(isExitConfirmDialogVisible = false) }
                }
                ReviewWriteIntent.ProhibitedFishConfirmClicked -> {
                    _state.update { it.copy(isProhibitedFishDialogVisible = false) }
                }
                ReviewWriteIntent.ProhibitedFishDialogDismissed -> {
                    _state.update { it.copy(isProhibitedFishDialogVisible = false) }
                }
            }
        }

        private fun handleInitialize(mode: ReviewWriteMode) {
            when (mode) {
                is ReviewWriteMode.Create -> {
                    val prefill = mode.prefill
                    _state.update {
                        it.copy(
                            mode = ReviewModeUi.CREATE,
                            exhibitId = prefill.exhibitId,
                            title = prefill.title,
                            hallName = prefill.hallName,
                            posterUrl = prefill.posterUrl,
                            appTopBarTitle = "리뷰 작성하기",
                            buttonText = "등록하기",
                            isInitializing = false,
                        )
                    }
                }

                is ReviewWriteMode.Edit -> {
                    val prefill = mode.prefill
                    _state.update {
                        it.copy(
                            mode = ReviewModeUi.EDIT,
                            reviewId = prefill.reviewId,
                            title = prefill.title,
                            hallName = prefill.hallName,
                            posterUrl = prefill.posterUrl,
                            appTopBarTitle = "리뷰 수정하기",
                            buttonText = "수정하기",
                            isInitializing = true,
                        )
                    }
                    fetchReviewDetail(prefill.reviewId)
                }
            }
        }

        private fun fetchReviewDetail(reviewId: Int) {
            viewModelScope.launch {
                getReviewDetailUseCase(reviewId = reviewId).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                        }
                        is ApiResult.Success -> {
                            val review = result.data
                            _state.update {
                                it.copy(
                                    visitDate = LocalDate.parse(review.visitDate),
                                    reviewText = review.content,
                                    photos =
                                        review.images.map { image ->
                                            ReviewPhotoItem.Remote(
                                                imageId = image.imageId,
                                                imageUrl = image.imageUrl,
                                            )
                                        },
                                    deletedImageIds = emptyList(),
                                    isInitializing = false,
                                )
                            }
                        }
                        is ApiResult.Error -> {
                            _state.update {
                                it.copy(isInitializing = false)
                            }
                        }
                    }
                }
            }
        }

        private fun submit() {
            val snapshot = _state.value

            if (snapshot.reviewText.isBlank() || snapshot.visitDate == null || snapshot.isSubmitting) {
                return
            }

            if (snapshot.reviewText.length < MIN_REVIEW_TEXT_LENGTH) {
                _state.update { it.copy(showReviewLengthError = true) }
                return
            }

            _state.update { it.copy(showReviewLengthError = false) }

            viewModelScope.launch {
                _state.update { it.copy(isSubmitting = true) }

                val newFiles =
                    snapshot.photos
                        .filterIsInstance<ReviewPhotoItem.Local>()
                        .mapNotNull { local ->
                            local.uri.copyToCacheFile(
                                context = appContext,
                                subDir = "review_upload",
                                filePrefix = "review_",
                            )
                        }

                when (snapshot.mode) {
                    ReviewModeUi.CREATE -> {
                        if ((snapshot.photos.isNotEmpty() && newFiles.isEmpty()) || snapshot.exhibitId <= 0) {
                            _state.update { it.copy(isSubmitting = false) }
                            return@launch
                        }

                        createReviewUseCase(
                            exhibitId = snapshot.exhibitId,
                            date = snapshot.visitDate.toString(),
                            content = snapshot.reviewText,
                            files = newFiles,
                        ).collect { result ->
                            when (result) {
                                is ApiResult.Loading -> Unit
                                is ApiResult.Success -> {
                                    _state.update { it.copy(isSubmitting = false) }
                                    _effect.emit(ReviewWriteEffect.NavigateBackWithToast(UiMessage.REVIEW_CREATED))
                                }
                                is ApiResult.Error -> {
                                    _state.update { it.copy(isSubmitting = false) }
                                    val error = result.error
                                    if (error is ApiError.HttpError && error.serverCode == "REVIEW400-BAD_WORD_INCLUDED") {
                                        _state.update { it.copy(isProhibitedFishDialogVisible = true) }
                                    } else {
                                        _effect.emit(ReviewWriteEffect.ShowToast(UiMessage.ERROR_RETRY))
                                    }
                                }
                            }
                        }
                    }

                    ReviewModeUi.EDIT -> {
                        if (snapshot.reviewId == null) return@launch

                        editReviewUseCase(
                            reviewId = snapshot.reviewId,
                            date = snapshot.visitDate.toString(),
                            content = snapshot.reviewText,
                            files = newFiles,
                            deletedImageIds = snapshot.deletedImageIds,
                        ).collect { result ->
                            when (result) {
                                is ApiResult.Loading -> Unit
                                is ApiResult.Success -> {
                                    _state.update { it.copy(isSubmitting = false) }
                                    _effect.emit(ReviewWriteEffect.NavigateBackWithToast(UiMessage.REVIEW_UPDATED))
                                }
                                is ApiResult.Error -> {
                                    _state.update { it.copy(isSubmitting = false) }
                                    val error = result.error
                                    if (error is ApiError.HttpError && error.serverCode == "REVIEW400-BAD_WORD_INCLUDED") {
                                        _state.update { it.copy(isProhibitedFishDialogVisible = true) }
                                    } else {
                                        _effect.emit(ReviewWriteEffect.ShowToast(UiMessage.ERROR_RETRY))
                                    }
                                }
                            }
                        }

                        _state.update { it.copy(isSubmitting = false) }
                    }
                }
            }
        }

        private fun mergePhotos(
            current: List<ReviewPhotoItem>,
            incoming: List<ReviewPhotoItem>,
            max: Int = MAX_REVIEW_PHOTO_COUNT,
        ): List<ReviewPhotoItem> = (current + incoming).take(max)
    }
