package com.arttrip.app.data.repository

import com.arttrip.app.data.remote.datasource.AuthDataSource
import com.arttrip.app.data.remote.mapper.auth.toDomain
import com.arttrip.app.data.remote.mapper.base.toAppError
import com.arttrip.app.data.remote.model.auth.LoginReqDto
import com.arttrip.app.domain.model.auth.LoginProvider
import com.arttrip.app.domain.model.auth.LoginResult
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.AuthRepository
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
                    val dto =
                        dataSource.postLogin(
                            loginReqDto =
                                LoginReqDto(
                                    provider = provider.value,
                                    idToken = idToken,
                                ),
                        )

                    val domainModel = dto.toDomain()

                    emit(ApiResult.Success(domainModel))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }
    }
