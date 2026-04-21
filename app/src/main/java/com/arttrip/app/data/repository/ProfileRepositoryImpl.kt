package com.arttrip.app.data.repository

import com.arttrip.app.core.model.image.ImageQueryParams
import com.arttrip.app.core.util.compressImageForUpload
import com.arttrip.app.core.util.toMultipartPart
import com.arttrip.app.data.remote.datasource.UserDataSource
import com.arttrip.app.data.remote.mapper.base.toAppError
import com.arttrip.app.data.remote.mapper.user.toDomain
import com.arttrip.app.data.remote.model.user.UserNicknameReqDto
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.model.profile.UserProfile
import com.arttrip.app.domain.repository.ProfileRepository
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

        override fun refreshProfile(imageQueryParams: ImageQueryParams): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val dto =
                        dataSource.getUserInfo(imageQueryParams)

                    val userProfile: UserProfile = dto.toDomain()
                    _profileState.value = userProfile
                    emit(ApiResult.Success(Unit))
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
                    val dto = dataSource.patchUserNickname(reqDto)

                    val userProfile: UserProfile = dto.toDomain()
                    _profileState.value = userProfile
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
                    val uploadFile = file.compressImageForUpload(targetMaxBytes = 1_500_000L)
                    val part = uploadFile.toMultipartPart(fieldName = "image")
                    val dto = dataSource.patchProfileImage(part)
                    val userProfile: UserProfile = dto.toDomain()
                    _profileState.value = userProfile
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }
    }
