package com.arttrip.app.domain.usecase.review

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteReviewUseCase
    @Inject
    constructor(
        private val reviewRepository: ReviewRepository,
    ) {
        operator fun invoke(reviewId: Int): Flow<ApiResult<Unit>> = reviewRepository.deleteReview(reviewId)
    }
