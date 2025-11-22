package com.arttrip.android.data.repository

import com.arttrip.android.data.remote.datasource.UserDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.user.toDomain
import com.arttrip.android.domain.model.network.ApiError
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.model.user.UserInfoModel
import com.arttrip.android.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val dataSource: UserDataSource,
    ) : UserRepository {
        override fun getUserInfo(id: Int): Flow<ApiResult<UserInfoModel>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val baseResponse = dataSource.getUserInfo(id = id)

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
