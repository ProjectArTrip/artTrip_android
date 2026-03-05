package com.arttrip.android.data.repository

import com.arttrip.android.core.util.toMultipartPart
import com.arttrip.android.data.remote.datasource.UserDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.user.toDomain
import com.arttrip.android.data.remote.model.user.UserNicknameReqDto
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.profile.UserProfile
import com.arttrip.android.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ProfileRepositoryImpl
    @Inject
    constructor(
        private val dataSource: UserDataSource,
    ) : ProfileRepository {
        override fun getProfile(): Flow<ApiResult<UserProfile>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val dto =
                        dataSource.getUserInfo()

                    val userProfile: UserProfile = dto.toDomain()

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
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }
    }
