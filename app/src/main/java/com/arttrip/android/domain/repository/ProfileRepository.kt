package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.profile.UserProfile
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProfileRepository {
    fun getProfile(): Flow<ApiResult<UserProfile>>

    fun deleteProfileImage(): Flow<ApiResult<Unit>>

    fun updateProfileImage(file: File): Flow<ApiResult<Unit>>
}
