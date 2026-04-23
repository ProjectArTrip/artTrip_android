package com.arttrip.app.data.repository

import com.arttrip.app.data.local.auth.TokenManager
import com.arttrip.app.data.remote.datasource.AuthDataSource
import com.arttrip.app.data.remote.mapper.auth.toDomain
import com.arttrip.app.data.remote.mapper.base.toAppError
import com.arttrip.app.data.remote.model.auth.DeleteUserAccountReqDto
import com.arttrip.app.data.remote.model.auth.LoginReqDto
import com.arttrip.app.domain.model.auth.LoginProvider
import com.arttrip.app.domain.model.auth.LoginResult
import com.arttrip.app.domain.model.auth.SocialLoginCredential
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AuthRepositoryImpl
    @Inject
    constructor(
        private val dataSource: AuthDataSource,
        private val tokenManager: TokenManager,
    ) : AuthRepository {
        override fun socialLogin(credential: SocialLoginCredential): Flow<ApiResult<LoginResult>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val dto =
                        dataSource.postLogin(
                            loginReqDto =
                                when (credential) {
                                    is SocialLoginCredential.Kakao ->
                                        LoginReqDto(
                                            provider = LoginProvider.KAKAO.value,
                                            idToken = credential.idToken,
                                        )
                                    is SocialLoginCredential.Google ->
                                        LoginReqDto(
                                            provider = LoginProvider.GOOGLE.value,
                                            authorizationCode = credential.authorizationCode,
                                        )
                                },
                        )

                    emit(ApiResult.Success(dto.toDomain()))
                } catch (e: Exception) {
                    emit(ApiResult.Error(e.toAppError()))
                }
            }

        override fun deleteUserAccount(): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val body =
                        DeleteUserAccountReqDto(
                            refreshToken = tokenManager.getRefreshToken().orEmpty(),
                        )
                    dataSource.deleteUserAccount(body)
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }
    }
