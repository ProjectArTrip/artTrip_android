package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.auth.LoginProvider
import com.arttrip.android.domain.model.auth.LoginResult
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.userkeyword.KeywordGroups
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun socialLogin(
        provider: LoginProvider,
        idToken: String,
    ): Flow<ApiResult<LoginResult>>

    fun getAllKeywords(): Flow<ApiResult<KeywordGroups>>

    fun saveUserKeywords(keywordIds: List<Int>): Flow<ApiResult<Unit>>
}
