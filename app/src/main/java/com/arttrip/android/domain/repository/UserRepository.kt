package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.user.UserInfoModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserInfo(id: Int): Flow<ApiResult<UserInfoModel>>
}
