package com.arttrip.android.data.repository

import com.arttrip.android.data.local.auth.TokenManager
import com.arttrip.android.data.remote.datasource.AuthDataSource
import com.arttrip.android.data.remote.mapper.auth.toDomain
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.model.auth.LoginRequestDto
import com.arttrip.android.domain.model.auth.AuthTokens
import com.arttrip.android.domain.model.auth.LoginModel
import com.arttrip.android.domain.model.auth.LoginProvider
import com.arttrip.android.domain.model.network.ApiError
import com.arttrip.android.domain.model.network.ApiResult
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
        ): Flow<ApiResult<LoginModel>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val baseResponse =
                        dataSource.postLogin(
                            loginRequestDto =
                                LoginRequestDto(
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
    }
