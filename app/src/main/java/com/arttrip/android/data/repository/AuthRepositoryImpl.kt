package com.arttrip.android.data.repository

import com.arttrip.android.data.remote.datasource.AuthDataSource
import com.arttrip.android.data.remote.mapper.auth.toDomain
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.model.auth.LoginReqDto
import com.arttrip.android.data.remote.model.auth.UserKeywordsReqDto
import com.arttrip.android.domain.model.auth.LoginProvider
import com.arttrip.android.domain.model.auth.LoginResult
import com.arttrip.android.domain.model.network.ApiError
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.usertaste.TasteGroupModel
import com.arttrip.android.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val dataSource: AuthDataSource,
    ) : AuthRepository {
        override fun socialLogin(
            provider: LoginProvider,
            idToken: String,
        ): Flow<ApiResult<LoginResult>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val baseResponse =
                        dataSource.postLogin(
                            loginReqDto =
                                LoginReqDto(
                                    provider = provider.value,
                                    idToken = idToken,
                                ),
                        )

                    val dto = baseResponse.result
                    if (dto == null) {
                        emit(
                            ApiResult.Error(
                                ApiError.HttpError(
                                    statusCode = 200,
                                    serverCode = "EMPTY_RESULT",
                                    serverMessage = "empty result",
                                ),
                            ),
                        )
                        return@flow
                    }

                    val domainModel = dto.toDomain()

                    emit(ApiResult.Success(domainModel))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getTasteGroups(): Flow<ApiResult<TasteGroupModel>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val baseResponse =
                        dataSource.getAllKeywords()

                    val dto = baseResponse.result
                    if (dto == null) {
                        emit(
                            ApiResult.Error(
                                ApiError.HttpError(
                                    statusCode = 200,
                                    serverCode = "EMPTY_RESULT",
                                    serverMessage = "empty result",
                                ),
                            ),
                        )
                        return@flow
                    }

                    val groups: TasteGroupModel = dto.toDomain()

                    emit(ApiResult.Success(groups))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun saveUserTaste(tasteIds: List<Int>): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val baseResponse =
                        dataSource.postUserKeywords(
                            userKeywordsReqDto =
                                UserKeywordsReqDto(
                                    keywordIds = tasteIds,
                                ),
                        )

                    if (baseResponse.isSuccess) {
                        emit(ApiResult.Success(Unit))
                    }
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }
    }
