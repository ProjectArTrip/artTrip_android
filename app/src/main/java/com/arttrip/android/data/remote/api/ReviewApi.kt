package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.model.network.BaseResponseDto
import com.arttrip.android.data.remote.api.ApiConstants.REVIEW_PATH
import com.arttrip.android.data.remote.model.review.ReviewDetailPageResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ReviewApi {
    @GET("${REVIEW_PATH}/{exhibitId}/detail")
    suspend fun getExhibitReviewsDetail(
        @Path("exhibitId") exhibitId: Long,
        @Query("cursor") cursor: Long? = null,
        @Query("size") size: Int = 10,
    ): BaseResponseDto<ReviewDetailPageResponseDto>
}