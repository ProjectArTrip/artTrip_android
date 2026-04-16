package com.arttrip.app.data.remote.datasource

import com.arttrip.app.data.remote.api.KeywordApi
import com.arttrip.app.data.remote.model.keyword.UserKeywordsReqDto
import javax.inject.Inject

class KeywordDataSource
    @Inject
    constructor(
        private val api: KeywordApi,
    ) {
        suspend fun getAllKeywords() = api.getAllKeywords()

        suspend fun getUserKeywords() = api.getUserKeywords()

        suspend fun getRecommendKeywords() = api.getRecommendKeywords()

        suspend fun postUserKeywords(userKeywordsReqDto: UserKeywordsReqDto) = api.postUserKeywords(body = userKeywordsReqDto)
    }
