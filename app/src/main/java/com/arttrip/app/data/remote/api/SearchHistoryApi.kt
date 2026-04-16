package com.arttrip.app.data.remote.api

import com.arttrip.app.data.remote.api.ApiConstants.SEARCH_KEYWORD_PATH
import com.arttrip.app.data.remote.model.searchhistory.SearchHistoryResponseDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchHistoryApi {
    @GET(SEARCH_KEYWORD_PATH)
    suspend fun getSearchHistory(): SearchHistoryResponseDto

    @DELETE("$SEARCH_KEYWORD_PATH/{id}")
    suspend fun deleteSearchHistory(
        @Path("id") id: Int,
    ): Unit

    @DELETE(SEARCH_KEYWORD_PATH)
    suspend fun deleteAllSearchHistory(): Unit
}
