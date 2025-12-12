package com.arttrip.android.data.repository

import com.arttrip.android.data.remote.datasource.HomeDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.home.toDomain
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
        override fun getCountryList(): Flow<ApiResult<List<String>>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val baseResponse = dataSource.getCountryList()

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

                    emit(ApiResult.Success(dto))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

    override fun getHomeRecommendExhibitList(isDomestic: Boolean): Flow<ApiResult<List<ExhibitModel>>> =
        flow {
            emit(ApiResult.Loading)

            try {
                val baseResponse = dataSource.getHomeRecommendToday(isDomestic = isDomestic)

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

    override fun getHomePersonalizedExhibitList(isDomestic: Boolean): Flow<ApiResult<List<ExhibitModel>>> =
        flow {
            emit(ApiResult.Loading)

            try {
                val baseResponse = dataSource.getHomePersonalized(isDomestic = isDomestic)

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
        isDomestic: Boolean,
        date: String
    ): Flow<ApiResult<List<ExhibitModel>>> =
        flow {
            emit(ApiResult.Loading)

            try {
                val baseResponse = dataSource.getHomeSchedule(isDomestic = isDomestic, date = date)

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
