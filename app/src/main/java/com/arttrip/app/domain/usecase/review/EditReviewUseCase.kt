package com.arttrip.app.domain.usecase.review

import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class EditReviewUseCase
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
