package com.arttrip.android.domain.usecase.review

import androidx.paging.PagingData
import com.arttrip.android.domain.model.review.Review
import com.arttrip.android.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetExhibitionReviewsUseCase
    @Inject
    constructor(
        private val reviewRepository: ReviewRepository,
    ) {
        val reviewTotalCount: StateFlow<Int?> = reviewRepository.exhibitReviewTotalCount

        fun clearReviewTotalCount() = reviewRepository.clearExhibitReviewTotalCount()

        operator fun invoke(exhibitId: Int): Flow<PagingData<Review>> =
            reviewRepository.getExhibitionReviews(
                exhibitId = exhibitId,
            )
    }
