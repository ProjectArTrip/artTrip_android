package com.arttrip.android.domain.usecase.review

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class UpdateReviewUseCase
    @Inject
    constructor(
        private val reviewRepository: ReviewRepository,
    ) {
        operator fun invoke(
            reviewId: Int,
            date: String,
            content: String,
            files: List<File>,
            deletedImageIds: List<Int>,
        ): Flow<ApiResult<Unit>> =
            reviewRepository.updateReview(
                reviewId,
                date,
                content,
                deletedImageIds,
                files,
            )
    }
