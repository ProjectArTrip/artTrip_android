package com.arttrip.android.domain.usecase.review

import androidx.paging.PagingData
import com.arttrip.android.domain.model.review.ReviewModel
import com.arttrip.android.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExhibitionReviewsUseCase
    @Inject
    constructor(
        private val reviewRepository: ReviewRepository,
    ) {
        operator fun invoke(exhibitId: Int): Flow<PagingData<ReviewModel>> =
            reviewRepository.getExhibitionReviews(
                exhibitId = exhibitId,
            )
    }
