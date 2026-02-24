package com.arttrip.android.data.remote.api

import com.arttrip.android.data.remote.api.ApiConstants.KEYWORD_PATH
import com.arttrip.android.data.remote.model.keyword.KeywordsResDto
import com.arttrip.android.data.remote.model.keyword.UserKeywordsReqDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface KeywordApi {
    @GET("${KEYWORD_PATH}/all")
    suspend fun getAllKeywords(): List<KeywordsResDto>

    @GET(KEYWORD_PATH)
    suspend fun getUserKeywords(): List<KeywordsResDto>

    @POST(KEYWORD_PATH)
    suspend fun postUserKeywords(
        @Body body: UserKeywordsReqDto,
    ): Unit
}
