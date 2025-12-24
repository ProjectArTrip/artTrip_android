package com.arttrip.android.data.repository

import com.arttrip.android.data.remote.datasource.FavoriteDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.favorite.toDomain
import com.arttrip.android.domain.model.bookmark.BookmarkResultModel
import com.arttrip.android.domain.model.network.ApiError
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class BookmarkRepositoryImpl
    @Inject
    constructor(
        private val dataSource: FavoriteDataSource,
    ) : BookmarkRepository {
        override fun addBookmark(exhibitId: Int): Flow<ApiResult<BookmarkResultModel>> =
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
                    if (e is CancellationException) throw e
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun removeBookmark(exhibitId: Int): Flow<ApiResult<Unit>> =
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
                    if (e is CancellationException) throw e
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }
    }
