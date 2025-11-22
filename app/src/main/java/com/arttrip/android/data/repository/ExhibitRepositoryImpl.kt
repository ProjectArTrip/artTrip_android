package com.arttrip.android.data.repository

import com.arttrip.android.data.remote.datasource.ExhibitDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.exhibit.toDomain
import com.arttrip.android.data.remote.mapper.user.toDomain
import com.arttrip.android.domain.model.exhibit.ExhibitInfoModel
import com.arttrip.android.domain.model.network.ApiError
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.ExhibitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExhibitRepositoryImpl
    @Inject
    constructor(
        private val dataSource: ExhibitDataSource,
    ) : ExhibitRepository {
        override fun getExhibitInfo(id: Int): Flow<ApiResult<ExhibitInfoModel>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val baseResponse = dataSource.getExhibitInfo(id = id) // BaseResponseDto<UserInfoResponseDto>

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
