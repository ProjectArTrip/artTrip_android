package com.arttrip.android.domain.repository

import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.profile.UserProfile
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProfileRepository {
    fun getProfile(imageQueryParams: ImageQueryParams): Flow<ApiResult<UserProfile>>

    fun updateUserNickname(nickname: String): Flow<ApiResult<Unit>>

    fun deleteProfileImage(): Flow<ApiResult<Unit>>

    fun updateProfileImage(file: File): Flow<ApiResult<Unit>>
}
