package com.arttrip.android.presentation.my.sub.myreviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.domain.model.review.UserReview
import com.arttrip.android.domain.usecase.review.GetUserReviewsUseCase
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsEffect
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsIntent
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private val REVIEW_THUMB_QUERY =
    ImageQueryParams(
        widthPx = 72,
        heightPx = 72,
    )

@HiltViewModel
class MyReviewsViewModel
    @Inject
    constructor(
        private val getUserReviewsUseCase: GetUserReviewsUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(MyReviewsState())
        val state = _state.asStateFlow()

        private val _effect = MutableSharedFlow<MyReviewsEffect>()
        val effect = _effect.asSharedFlow()

        init {
            loadReviews()
        }

        private var reviewCountJob: Job? = null

        fun onIntent(intent: MyReviewsIntent) {
            when (intent) {
                MyReviewsIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(MyReviewsEffect.NavigateBack) }
                }
                MyReviewsIntent.DeleteReviewClicked -> {
                    _state.update { it.copy(isRemoveDialogVisible = true) }
                }
                is MyReviewsIntent.EditReviewClicked -> {
                    val review = intent.review
                    viewModelScope.launch {
                        _effect.emit(
                            MyReviewsEffect.NavigateToReviewEdit(
                                id = review.id,
                                title = review.exhibitionTitle,
                                hallName = "예시 hallName",
                                posterUrl = review.thumbnailUrl,
                                reviewText = review.content,
                            ),
                        )
                    }
                }
                MyReviewsIntent.RemoveConfirmClicked -> {
                    _state.update { it.copy(isRemoveDialogVisible = false) }

                    // TODO 리뷰삭제API
                }
                MyReviewsIntent.RemoveDialogDismissed -> _state.update { it.copy(isRemoveDialogVisible = false) }
            }
        }

        val reviewsFlow: Flow<PagingData<UserReview>> =
            getUserReviewsUseCase().cachedIn(viewModelScope)

        private fun loadReviews() {
            getUserReviewsUseCase.clearReviewTotalCount()
            _state.update { it.copy(reviewTotalCount = null) }

            reviewCountJob?.cancel()
            reviewCountJob =
                viewModelScope.launch {
                    getUserReviewsUseCase.reviewTotalCount.collectLatest { count ->
                        _state.update { it.copy(reviewTotalCount = count) }
                    }
                }
        }
    }
