package com.arttrip.android.domain.usecase.review

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class CreateReviewUseCase
    @Inject
    constructor(
        private val reviewRepository: ReviewRepository,
    ) {
        operator fun invoke(
            exhibitId: Int,
            date: String,
            content: String,
            files: List<File>,
        ): Flow<ApiResult<Unit>> =
            reviewRepository.createReview(
                exhibitId,
                date,
                content,
                files,
            )
    }
