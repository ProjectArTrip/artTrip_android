package com.arttrip.app.data.repository

import com.arttrip.app.data.remote.datasource.UserDataSource
import com.arttrip.app.data.remote.mapper.base.toAppError
import com.arttrip.app.data.remote.model.user.UserFcmTokenReqDto
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class NotificationRepositoryImpl
    @Inject
    constructor(
        private val userDataSource: UserDataSource,
    ) : NotificationRepository {
        override fun registerUserFcmToken(fcmToken: String): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)
                try {
                    userDataSource.postUserFcmToken(
                        UserFcmTokenReqDto(token = fcmToken),
                    )
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }
    }
