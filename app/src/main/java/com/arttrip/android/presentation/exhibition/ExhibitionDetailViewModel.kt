package com.arttrip.android.presentation.exhibition

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.core.util.bookmark.BookmarkStore
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.review.ExhibitionReview
import com.arttrip.android.domain.usecase.exhibition.GetExhibitionDetailUseCase
import com.arttrip.android.domain.usecase.review.GetExhibitionReviewsUseCase
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailEffect
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailIntent
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExhibitionDetailViewModel
    @Inject
    constructor(
        private val getExhibitionDetailUseCase: GetExhibitionDetailUseCase,
        private val getExhibitionReviewsUseCase: GetExhibitionReviewsUseCase,
        private val bookmarkStore: BookmarkStore,
    ) : ViewModel() {
        private val _state = MutableStateFlow(ExhibitionDetailState())
        val state: StateFlow<ExhibitionDetailState> = _state

        private val _effect = MutableSharedFlow<ExhibitionDetailEffect>()
        val effect: SharedFlow<ExhibitionDetailEffect> = _effect

        private var reviewCountJob: Job? = null

        init {
            viewModelScope.launch {
                state
                    .map { it.detail?.exhibitId }
                    .distinctUntilChanged()
                    .filterNotNull()
                    .collectLatest { id ->
                        bookmarkStore
                            .bookmarkedFlow(id)
                            .collectLatest { bookmarked ->
                                _state.update { it.copy(isBookmarked = bookmarked) }
                            }
                    }
            }
        }

        fun onIntent(intent: ExhibitionDetailIntent) {
            when (intent) {
                is ExhibitionDetailIntent.Initialize -> {
                    initialize(intent.exhibitId, intent.imageQueryParams)
                }

                is ExhibitionDetailIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(ExhibitionDetailEffect.NavigateBack) }
                }

                is ExhibitionDetailIntent.BookmarkClicked -> {
                    val exhibitId = _state.value.detail?.exhibitId ?: return
                    bookmarkStore.toggle(exhibitId)
                }

                is ExhibitionDetailIntent.WriteReviewClicked -> {
                    _state.update {
                        it.copy(
                            writeReviewDialogVisible = true,
                        )
                    }
                }

                ExhibitionDetailIntent.WriteReviewDialogDismissClicked -> {
                    _state.update { it.copy(writeReviewDialogVisible = false) }
                }

                ExhibitionDetailIntent.WriteReviewConfirmClicked -> {
                    val detail = state.value.detail ?: return

                    _state.update { it.copy(writeReviewDialogVisible = false) }

                    viewModelScope.launch {
                        _effect.emit(
                            ExhibitionDetailEffect.NavigateToWriteReview(
                                exhibitId = detail.exhibitId,
                                title = detail.title,
                                hallName = detail.hallName.orEmpty(),
                                posterUrl = detail.posterUrl,
                            ),
                        )
                    }
                }

                ExhibitionDetailIntent.OnReviewWriteSuccess -> {
                    viewModelScope.launch { _effect.emit(ExhibitionDetailEffect.RefreshReviews) }
                }
            }
        }

        fun reviewsFlow(exhibitId: Int): Flow<PagingData<ExhibitionReview>> =
            getExhibitionReviewsUseCase(exhibitId).cachedIn(viewModelScope)

        private fun initialize(
            exhibitId: Int,
            imageQueryParams: ImageQueryParams,
        ) {
            fetchExhibitionDetail(exhibitId, imageQueryParams)

            getExhibitionReviewsUseCase.clearReviewTotalCount()
            _state.update { it.copy(reviewTotalCount = null) }

            reviewCountJob?.cancel()
            reviewCountJob =
                viewModelScope.launch {
                    getExhibitionReviewsUseCase.reviewTotalCount.collectLatest { count ->
                        _state.update { it.copy(reviewTotalCount = count) }
                    }
                }
        }

        private fun fetchExhibitionDetail(
            exhibitId: Int,
            imageQueryParams: ImageQueryParams,
        ) {
            viewModelScope.launch {
                getExhibitionDetailUseCase(exhibitId, imageQueryParams).collect { result ->
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
                            bookmarkStore.setFromRemote(result.data.exhibitId, result.data.isBookmarked)
                        }

                        is ApiResult.Error -> {
                            Log.d("ExhibitDetail", "${result.error}")

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
    }
