package com.arttrip.app.domain.repository

import com.arttrip.app.domain.model.auth.LoginProvider
import com.arttrip.app.domain.model.auth.LoginResult
import com.arttrip.app.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun socialLogin(
        provider: LoginProvider,
        idToken: String,
    ): Flow<ApiResult<LoginResult>>

    fun deleteUserAccount(): Flow<ApiResult<Unit>>
}
