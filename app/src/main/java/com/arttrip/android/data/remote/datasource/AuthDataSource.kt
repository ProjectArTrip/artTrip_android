package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.AuthApi
import com.arttrip.android.data.remote.model.auth.LoginReqDto
import com.arttrip.android.data.remote.model.auth.UserKeywordsReqDto
import javax.inject.Inject

class AuthDataSource
    @Inject
    constructor(
        private val api: AuthApi,
    ) {
        suspend fun postLogin(loginReqDto: LoginReqDto) = api.postLogin(loginReqDto)

        suspend fun getAllKeywords() = api.getAllKeywords()

        suspend fun postUserKeywords(userKeywordsReqDto: UserKeywordsReqDto) = api.postUserKeywords(userKeywordsReqDto)
    }
