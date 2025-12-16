package com.arttrip.android.data.repository

import ExhibitListQueryModel
import com.arttrip.android.data.remote.datasource.HomeDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.home.toDomain
import com.arttrip.android.data.remote.mapper.home.toRequestDto
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiError
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl
    @Inject
    constructor(
        private val dataSource: HomeDataSource,
    ) : HomeRepository {
        override fun getHomeRecommendExhibitList(query: ExhibitListQueryModel): Flow<ApiResult<List<ExhibitModel>>> =
            flow {
                try {
                    val requestDto = query.toRequestDto()
                    val baseResponse = dataSource.getHomeRecommendToday(requestDto = requestDto)

                    val responseDto = baseResponse.result
                    if (responseDto == null) {
                        emit(
                            ApiResult.Error(
                                ApiError.HttpError(
                                    statusCode = -1,
                                    serverCode = "EMPTY_RESULT",
                                    serverMessage = "empty result",
                                ),
                            ),
                        )
                        return@flow
                    }

                    val domain = responseDto.toDomain()

                    emit(ApiResult.Success(domain))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getHomePersonalizedExhibitList(query: ExhibitListQueryModel): Flow<ApiResult<List<ExhibitModel>>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val requestDto = query.toRequestDto()
                    val baseResponse = dataSource.getHomePersonalizedRandom(requestDto = requestDto)

                    val dto = baseResponse.result
                    if (dto == null) {
                        emit(
                            ApiResult.Error(
                                ApiError.HttpError(
                                    statusCode = -1,
                                    serverCode = "EMPTY_RESULT",
                                    serverMessage = "empty result",
                                ),
                            ),
                        )
                        return@flow
                    }

                    val domain = dto.toDomain()

                    emit(ApiResult.Success(domain))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getHomeScheduleExhibitList(
            query: ExhibitListQueryModel
        ): Flow<ApiResult<List<ExhibitModel>>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val requestDto = query.toRequestDto()
                    val baseResponse = dataSource.getHomeSchedule(requestDto = requestDto)

                    val dto = baseResponse.result
                    if (dto == null) {
                        emit(
                            ApiResult.Error(
                                ApiError.HttpError(
                                    statusCode = -1,
                                    serverCode = "EMPTY_RESULT",
                                    serverMessage = "empty result",
                                ),
                            ),
                        )
                        return@flow
                    }

                    val domain = dto.toDomain()

                    emit(ApiResult.Success(domain))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

    override fun getHomeGenreExhibitList(query: ExhibitListQueryModel): Flow<ApiResult<List<ExhibitModel>>> =
        flow {
            emit(ApiResult.Loading)

            try {
                val requestDto = query.toRequestDto()
                val baseResponse = dataSource.getHomeGenreRandom(requestDto = requestDto)

                val dto = baseResponse.result
                if (dto == null) {
                    emit(
                        ApiResult.Error(
                            ApiError.HttpError(
                                statusCode = -1,
                                serverCode = "EMPTY_RESULT",
                                serverMessage = "empty result",
                            ),
                        ),
                    )
                    return@flow
                }

                val domain = dto.toDomain()

                emit(ApiResult.Success(domain))
            } catch (e: Exception) {
                val error = e.toAppError()
                emit(ApiResult.Error(error))
            }
        }
}
