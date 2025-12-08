package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.AuthApi
import com.arttrip.android.data.remote.model.auth.LoginRequestDto
import com.arttrip.android.data.remote.model.auth.UserKeywordsRequestDto
import javax.inject.Inject

class AuthDataSource
    @Inject
    constructor(
        private val api: AuthApi,
    ) {
        suspend fun postLogin(loginRequestDto: LoginRequestDto) = api.postLogin(loginRequestDto)

        suspend fun getAllKeywords() = api.getAllKeywords()

        suspend fun postUserKeywords(userKeywordsRequestDto: UserKeywordsRequestDto) = api.postUserKeywords(userKeywordsRequestDto)
    }
