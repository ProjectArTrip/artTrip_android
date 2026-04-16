package com.arttrip.app.domain.usecase.review

import androidx.paging.PagingData
import com.arttrip.app.domain.model.review.ExhibitionReview
import com.arttrip.app.domain.repository.ReviewRepository
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

        operator fun invoke(exhibitId: Int): Flow<PagingData<ExhibitionReview>> =
            reviewRepository.getExhibitionReviews(
                exhibitId = exhibitId,
            )
    }
