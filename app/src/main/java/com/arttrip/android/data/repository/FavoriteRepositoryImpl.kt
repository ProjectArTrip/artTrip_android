package com.arttrip.android.data.repository

import com.arttrip.android.data.remote.datasource.FavoriteDataSource
import com.arttrip.android.data.remote.mapper.auth.toDomain
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.favorite.toDomain
import com.arttrip.android.domain.model.favorite.FavoriteCheckModel
import com.arttrip.android.domain.model.favorite.FavoriteResult
import com.arttrip.android.domain.model.network.ApiError
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepositoryImpl
    @Inject
    constructor(
        private val dataSource: FavoriteDataSource,
    ) : FavoriteRepository {
        override fun addFavorite(exhibitId: Int): Flow<ApiResult<FavoriteResult>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val baseResponse =
                        dataSource.postFavorite(
                            exhibitId,
                        )

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

        override fun removeFavorite(exhibitId: Int): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val baseResponse =
                        dataSource.deleteFavorite(
                            exhibitId,
                        )

                    if (baseResponse.isSuccess) {
                        emit(ApiResult.Success(Unit))
                    }
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun checkFavorite(exhibitId: Int): Flow<ApiResult<FavoriteCheckModel>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val baseResponse =
                        dataSource.getIsFavorite(
                            exhibitId,
                        )

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
