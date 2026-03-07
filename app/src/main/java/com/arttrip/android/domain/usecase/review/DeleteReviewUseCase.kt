package com.arttrip.android.domain.usecase.review

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteReviewUseCase
    @Inject
    constructor(
        private val reviewRepository: ReviewRepository,
    ) {
        operator fun invoke(reviewId: Int): Flow<ApiResult<Unit>> = reviewRepository.deleteReview(reviewId)
    }
