package com.arttrip.app.domain.usecase.review

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.model.review.ReviewDetail
import com.arttrip.app.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewDetailUseCase
    @Inject
    constructor(
        private val repo: ReviewRepository,
    ) {
        operator fun invoke(reviewId: Int): Flow<ApiResult<ReviewDetail>> = repo.getReview(reviewId)
    }
