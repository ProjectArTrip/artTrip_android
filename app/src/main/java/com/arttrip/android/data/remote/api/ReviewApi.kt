package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.REVIEW_PATH
import com.arttrip.android.data.remote.model.review.ExhibitReviewDto
import com.arttrip.android.data.remote.model.review.ReviewPageResDto
import com.arttrip.android.data.remote.model.review.UserReviewDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewApi {
    @GET("${REVIEW_PATH}/{exhibitId}")
    suspend fun getExhibitDetailReviews(
        @Path("exhibitId") exhibitId: Int,
        @Query("cursor") cursor: Int? = null,
        @Query("size") size: Int = 10,
        @Query("w") w: Int = 200,
        @Query("h") h: Int = 200,
        @Query("f") f: String = "webp",
    ): ReviewPageResDto<ExhibitReviewDto>

    @GET("${REVIEW_PATH}/all")
    suspend fun getUserReviews(
        @Query("cursor") cursor: Int? = null,
        @Query("size") size: Int = 10,
        @Query("w") w: Int = 200,
        @Query("h") h: Int = 200,
        @Query("f") f: String = "webp",
    ): ReviewPageResDto<UserReviewDto>
}
