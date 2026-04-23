package com.arttrip.app.data.remote.datasource

import com.arttrip.app.data.remote.api.AuthApi
import com.arttrip.app.data.remote.model.auth.DeleteUserAccountReqDto
import com.arttrip.app.data.remote.model.auth.LoginReqDto
import javax.inject.Inject

class AuthDataSource
    @Inject
    constructor(
        private val api: AuthApi,
    ) {
        suspend fun postLogin(loginReqDto: LoginReqDto) = api.postLogin(loginReqDto)

        suspend fun deleteUserAccount(body: DeleteUserAccountReqDto) = api.withdrawAccount(body)
    }
