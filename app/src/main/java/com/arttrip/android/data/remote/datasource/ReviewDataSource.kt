package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.ReviewApi
import com.arttrip.android.data.remote.model.review.ExhibitReviewDto
import com.arttrip.android.data.remote.model.review.ReviewPageResDto
import com.arttrip.android.data.remote.model.review.UserReviewDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ReviewDataSource
    @Inject
    constructor(
        private val api: ReviewApi,
    ) {
        suspend fun getExhibitDetailReviews(
            exhibitId: Int,
            cursor: Int?,
            size: Int,
        ): ReviewPageResDto<ExhibitReviewDto> =
            api.getExhibitDetailReviews(
                exhibitId = exhibitId,
                cursor = cursor,
                size = size,
            )

        suspend fun postReview(
            exhibitId: Int,
            request: RequestBody,
            parts: List<MultipartBody.Part>?,
        ) = api.postReview(
            exhibitId = exhibitId,
            request = request,
            images = parts,
        )

        suspend fun deleteReview(reviewId: Int) = api.deleteReview(reviewId = reviewId)

        suspend fun getUserReviews(
            cursor: Int?,
            size: Int,
        ): ReviewPageResDto<UserReviewDto> =
            api.getUserReviews(
                cursor = cursor,
                size = size,
            )
    }
