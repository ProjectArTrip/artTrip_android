package com.arttrip.android.presentation.my.sub.myreviews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.review.UserReview
import com.arttrip.android.domain.usecase.review.DeleteReviewUseCase
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
        private val deleteReviewUseCase: DeleteReviewUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(MyReviewsState())
        val state = _state.asStateFlow()

        private val _effect = MutableSharedFlow<MyReviewsEffect>()
        val effect = _effect.asSharedFlow()

        val reviewsFlow: Flow<PagingData<UserReview>> =
            getUserReviewsUseCase().cachedIn(viewModelScope)

        init {
            loadReviews()
        }

        private var reviewCountJob: Job? = null

        fun onIntent(intent: MyReviewsIntent) {
            when (intent) {
                MyReviewsIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(MyReviewsEffect.NavigateBack) }
                }
                is MyReviewsIntent.DeleteReviewClicked -> {
                    _state.update {
                        it.copy(
                            isRemoveDialogVisible = true,
                            selectedReviewId = intent.reviewId,
                        )
                    }
                }
                is MyReviewsIntent.EditReviewClicked -> {
                    val review = intent.review
                    viewModelScope.launch {
                        _effect.emit(
                            MyReviewsEffect.NavigateToReviewEdit(
                                reviewId = review.id,
                                title = review.exhibitionTitle,
                                hallName = review.hallName,
                                posterUrl = review.posterUrl,
                            ),
                        )
                    }
                }
                MyReviewsIntent.RemoveConfirmClicked -> {
                    val reviewId = _state.value.selectedReviewId ?: return

                    _state.update { it.copy(isRemoveDialogVisible = false) }

                    deleteReview(reviewId)
                }
                MyReviewsIntent.RemoveDialogDismissed -> _state.update { it.copy(isRemoveDialogVisible = false) }

                MyReviewsIntent.OnReviewEditSuccess -> viewModelScope.launch { _effect.emit(MyReviewsEffect.RefreshReviews) }
            }
        }

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

        private fun deleteReview(reviewId: Int) {
            viewModelScope.launch {
                deleteReviewUseCase(reviewId).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true,
                                )
                            }
                        }
                        is ApiResult.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    selectedReviewId = null,
                                )
                            }
                            _effect.emit(MyReviewsEffect.RefreshReviews)
                        }
                        is ApiResult.Error -> {
                            Log.d("MyReviews", "${result.error}")
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    selectedReviewId = null,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
