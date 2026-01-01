package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.REVIEW_PATH
import com.arttrip.android.data.remote.model.network.BaseResponseDto
import com.arttrip.android.data.remote.model.review.ReviewDetailPageResDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewApi {
    @GET("${REVIEW_PATH}/{exhibitId}/detail")
    suspend fun getExhibitDetailReviews(
        @Path("exhibitId") exhibitId: Int,
        @Query("cursor") cursor: Int? = null,
        @Query("size") size: Int = 10,
        @Query("w") w: Int = 200,
        @Query("h") h: Int = 200,
        @Query("f") f: String = "webp",
    ): BaseResponseDto<ReviewDetailPageResDto>
}
