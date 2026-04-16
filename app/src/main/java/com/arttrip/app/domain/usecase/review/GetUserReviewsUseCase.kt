package com.arttrip.app.domain.usecase.review

import androidx.paging.PagingData
import com.arttrip.app.domain.model.review.UserReview
import com.arttrip.app.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetUserReviewsUseCase
    @Inject
    constructor(
        private val reviewRepository: ReviewRepository,
    ) {
        val reviewTotalCount: StateFlow<Int?> = reviewRepository.userReviewTotalCount

        fun clearReviewTotalCount() = reviewRepository.clearUserReviewTotalCount()

        operator fun invoke(): Flow<PagingData<UserReview>> = reviewRepository.getUserReviews()
    }
