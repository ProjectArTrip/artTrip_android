package com.arttrip.android.domain.repository

import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.profile.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

interface ProfileRepository {
    val profileState: StateFlow<UserProfile?>

    fun refreshProfile(imageQueryParams: ImageQueryParams): Flow<ApiResult<Unit>>

    fun updateUserNickname(nickname: String): Flow<ApiResult<Unit>>

    fun deleteUserAccount(): Flow<ApiResult<Unit>>

    fun deleteProfileImage(): Flow<ApiResult<Unit>>

    fun updateProfileImage(file: File): Flow<ApiResult<Unit>>
}
