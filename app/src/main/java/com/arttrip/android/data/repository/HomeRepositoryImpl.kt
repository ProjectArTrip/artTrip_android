package com.arttrip.android.data.repository

import ExhibitListQueryModel
import com.arttrip.android.data.remote.datasource.HomeDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.home.toDomain
import com.arttrip.android.data.remote.mapper.home.toGenreRequestDto
import com.arttrip.android.data.remote.mapper.home.toRequestDto
import com.arttrip.android.data.remote.mapper.home.toScheduleRequestDto
import com.arttrip.android.data.remote.model.home.DomesticExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticGenreExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.DomesticScheduleExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignGenreExhibitListRequestDto
import com.arttrip.android.data.remote.model.home.ForeignScheduleExhibitListRequestDto
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiError
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class HomeRepositoryImpl
    @Inject
    constructor(
        private val dataSource: HomeDataSource,
    ) : HomeRepository {
        override fun getHomeRecommendExhibitList(place: Place): Flow<ApiResult<List<ExhibitModel>>> =
            flow {
                try {
                    val requestDto = place.toRequestDto()

                    val baseResponse = when (requestDto) {
                        is ForeignExhibitListRequestDto -> dataSource.getHomeRecommendToday(requestDto)
                        is DomesticExhibitListRequestDto -> dataSource.getHomeRecommendToday(requestDto)
                    }

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

        override fun getHomePersonalizedExhibitList(place: Place): Flow<ApiResult<List<ExhibitModel>>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val requestDto = place.toRequestDto()

                    val baseResponse = when (requestDto) {
                        is ForeignExhibitListRequestDto -> dataSource.getHomePersonalizedRandom(requestDto)
                        is DomesticExhibitListRequestDto -> dataSource.getHomePersonalizedRandom(requestDto)
                    }

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
            place: Place, date: LocalDate
        ): Flow<ApiResult<List<ExhibitModel>>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val requestDto = place.toScheduleRequestDto(date = date)

                    val baseResponse = when (requestDto) {
                        is ForeignScheduleExhibitListRequestDto -> dataSource.getHomeSchedule(requestDto)
                        is DomesticScheduleExhibitListRequestDto -> dataSource.getHomeSchedule(requestDto)
                    }

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

    override fun getHomeGenreExhibitList(place: Place, genre: ExhibitGenre): Flow<ApiResult<List<ExhibitModel>>> =
        flow {
            emit(ApiResult.Loading)

            try {
                val requestDto = place.toGenreRequestDto(genre = genre)

                val baseResponse = when (requestDto) {
                    is ForeignGenreExhibitListRequestDto -> dataSource.getHomeGenreRandom(requestDto)
                    is DomesticGenreExhibitListRequestDto -> dataSource.getHomeGenreRandom(requestDto)
                }

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
