package com.arttrip.app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arttrip.app.data.remote.datasource.UserDataSource
import com.arttrip.app.data.remote.datasource.UserNoticeDataSource
import com.arttrip.app.data.remote.mapper.base.toAppError
import com.arttrip.app.data.remote.model.user.UserFcmTokenReqDto
import com.arttrip.app.data.remote.model.user.UserPushEnabledReqDto
import com.arttrip.app.data.remote.paging.usernotice.NotificationPagingSource
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.model.notification.Notification
import com.arttrip.app.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class NotificationRepositoryImpl
    @Inject
    constructor(
        private val userDataSource: UserDataSource,
        private val userNoticeDataSource: UserNoticeDataSource,
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

        override fun getNotifications(
            pageSize: Int,
            initialLoadSize: Int,
        ): Flow<PagingData<Notification>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = pageSize,
                        initialLoadSize = initialLoadSize,
                        prefetchDistance = 1,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    NotificationPagingSource(
                        dataSource = userNoticeDataSource,
                    )
                },
            ).flow

        override fun readNotification(userNoticeId: Int): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)
                try {
                    userNoticeDataSource.readNotification(userNoticeId)
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        override fun getHasUnread(): Flow<ApiResult<Boolean>> =
            flow {
                emit(ApiResult.Loading)
                try {
                    val hasUnread = userNoticeDataSource.getReadStatus()
                    emit(ApiResult.Success(hasUnread))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        override fun getPushEnabled(): Flow<ApiResult<Boolean>> =
            flow {
                emit(ApiResult.Loading)
                try {
                    val result = userDataSource.getPushEnabled()
                    emit(ApiResult.Success(result.enabled))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }

        override fun updatePushEnabled(isEnabled: Boolean): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)
                try {
                    userDataSource.patchPushEnabled(UserPushEnabledReqDto(enabled = isEnabled))
                    emit(ApiResult.Success(Unit))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }
    }
