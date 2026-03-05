package com.arttrip.android.data.repository

import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.core.util.toMultipartPart
import com.arttrip.android.data.remote.datasource.UserDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.user.toDomain
import com.arttrip.android.data.remote.model.user.UserNicknameReqDto
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.profile.UserProfile
import com.arttrip.android.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ProfileRepositoryImpl
    @Inject
    constructor(
        private val dataSource: UserDataSource,
    ) : ProfileRepository {
        private val _profileState = MutableStateFlow<UserProfile?>(null)
        override val profileState: StateFlow<UserProfile?> = _profileState

        override fun refreshProfile(imageQueryParams: ImageQueryParams): Flow<ApiResult<UserProfile>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val dto =
                        dataSource.getUserInfo(imageQueryParams)

                    val userProfile: UserProfile = dto.toDomain()
                    _profileState.value = userProfile
                    emit(ApiResult.Success(userProfile))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        override fun updateUserNickname(nickname: String): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val reqDto =
                        UserNicknameReqDto(
                            nickName = nickname,
                        )
                    dataSource.patchUserNickname(reqDto)
                    _profileState.update { it?.copy(nickname = nickname) ?: it }
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        override fun deleteProfileImage(): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    dataSource.deleteProfileImage()
                    _profileState.update { it?.copy(profileImageUrl = null) ?: it }
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        override fun updateProfileImage(file: File): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)
                try {
                    val part = file.toMultipartPart(fieldName = "image")
                    dataSource.patchProfileImage(part)
                    fetchProfile()
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        private suspend fun fetchProfile(): UserProfile {
            val dto = dataSource.getUserInfo(ImageQueryParams(widthPx = 96, heightPx = 96))
            val profile = dto.toDomain()
            _profileState.value = profile
            return profile
        }
    }
