package com.arttrip.android.data.repository

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.data.remote.datasource.HomeDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.home.toDomesticDomain
import com.arttrip.android.data.remote.mapper.home.toForeignDomain
import com.arttrip.android.data.remote.mapper.home.toGenreRequestDto
import com.arttrip.android.data.remote.mapper.home.toPersonalizedRequestDto
import com.arttrip.android.data.remote.mapper.home.toRecommendRequestDto
import com.arttrip.android.data.remote.mapper.home.toScheduleRequestDto
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.domain.model.network.ApiError
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class HomeRepositoryImpl
    @Inject
    constructor(
        private val dataSource: HomeDataSource,
    ) : HomeRepository {
        override fun getForeignRecommendExhibitList(
            country: ForeignCountry,
            width: Int,
            height: Int,
            format: String,
        ): Flow<ApiResult<List<ExhibitionModel>>> =
            flow {
                try {
                    val requestDto = country.toRecommendRequestDto()

                    val baseResponse =
                        dataSource.getHomeRecommendToday(
                            requestDto = requestDto,
                            width = width,
                            height = height,
                            format = format,
                        )
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

                    val domain = responseDto.toForeignDomain()

                    emit(ApiResult.Success(domain))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getForeignPersonalizedExhibitList(
            country: ForeignCountry,
            width: Int,
            height: Int,
            format: String,
        ): Flow<ApiResult<List<ExhibitionModel>>> =
            flow {
                try {
                    val requestDto = country.toPersonalizedRequestDto()

                    val baseResponse =
                        dataSource.getHomePersonalizedRandom(
                            requestDto = requestDto,
                            width = width,
                            height = height,
                            format = format,
                        )
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

                    val domain = responseDto.toForeignDomain()

                    emit(ApiResult.Success(domain))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getForeignScheduleExhibitList(
            country: ForeignCountry,
            date: LocalDate,
            width: Int,
            height: Int,
            format: String,
        ): Flow<ApiResult<List<ExhibitionModel>>> =
            flow {
                try {
                    val requestDto = country.toScheduleRequestDto(date = date)

                    val baseResponse = dataSource.getHomeSchedule(requestDto = requestDto, width = width, height = height, format = format)
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

                    val domain = responseDto.toForeignDomain()

                    emit(ApiResult.Success(domain))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getForeignGenreExhibitList(
            country: ForeignCountry,
            genre: ExhibitionGenre,
            width: Int,
            height: Int,
            format: String,
        ): Flow<ApiResult<List<ExhibitionModel>>> =
            flow {
                try {
                    val requestDto = country.toGenreRequestDto(genre = genre)

                    val baseResponse =
                        dataSource.getHomeGenreRandom(
                            requestDto = requestDto,
                            width = width,
                            height = height,
                            format = format,
                        )
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

                    val domain = responseDto.toForeignDomain()

                    emit(ApiResult.Success(domain))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getDomesticRecommendExhibitList(
            region: DomesticRegion,
            width: Int,
            height: Int,
            format: String,
        ): Flow<ApiResult<List<ExhibitionModel>>> =
            flow {
                try {
                    val requestDto = region.toRecommendRequestDto()

                    val baseResponse =
                        dataSource.getHomeRecommendToday(
                            requestDto = requestDto,
                            width = width,
                            height = height,
                            format = format,
                        )
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

                    val domain = responseDto.toDomesticDomain()

                    emit(ApiResult.Success(domain))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getDomesticPersonalizedExhibitList(
            region: DomesticRegion,
            width: Int,
            height: Int,
            format: String,
        ): Flow<ApiResult<List<ExhibitionModel>>> =
            flow {
                try {
                    val requestDto = region.toPersonalizedRequestDto()

                    val baseResponse =
                        dataSource.getHomePersonalizedRandom(
                            requestDto = requestDto,
                            width = width,
                            height = height,
                            format = format,
                        )
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

                    val domain = responseDto.toDomesticDomain()

                    emit(ApiResult.Success(domain))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getDomesticScheduleExhibitList(
            region: DomesticRegion,
            date: LocalDate,
            width: Int,
            height: Int,
            format: String,
        ): Flow<ApiResult<List<ExhibitionModel>>> =
            flow {
                try {
                    val requestDto = region.toScheduleRequestDto(date = date)

                    val baseResponse = dataSource.getHomeSchedule(requestDto = requestDto, width = width, height = height, format = format)
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

                    val domain = responseDto.toDomesticDomain()

                    emit(ApiResult.Success(domain))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getDomesticGenreExhibitList(
            region: DomesticRegion,
            genre: ExhibitionGenre,
            width: Int,
            height: Int,
            format: String,
        ): Flow<ApiResult<List<ExhibitionModel>>> =
            flow {
                try {
                    val requestDto = region.toGenreRequestDto(genre = genre)

                    val baseResponse =
                        dataSource.getHomeGenreRandom(
                            requestDto = requestDto,
                            width = width,
                            height = height,
                            format = format,
                        )
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

                    val domain = responseDto.toDomesticDomain()

                    emit(ApiResult.Success(domain))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }
    }
