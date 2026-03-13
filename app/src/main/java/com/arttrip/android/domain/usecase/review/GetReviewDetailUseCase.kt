package com.arttrip.android.domain.usecase.review

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.review.ReviewDetail
import com.arttrip.android.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewDetailUseCase
    @Inject
    constructor(
        private val repo: ReviewRepository,
    ) {
        operator fun invoke(reviewId: Int): Flow<ApiResult<ReviewDetail>> = repo.getReview(reviewId)
    }
