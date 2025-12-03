package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.auth.LoginModel
import com.arttrip.android.domain.model.auth.LoginProvider
import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun socialLogin(
        provider: LoginProvider,
        idToken: String,
    ): Flow<ApiResult<LoginModel>>
}
