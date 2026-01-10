package com.arttrip.android.data.repository

import android.util.Log
import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.data.remote.datasource.ExhibitDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.exhibit.toDomain
import com.arttrip.android.domain.model.exhibition.ExhibitionDetail
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
        override fun getExhibitDetail(
            exhibitId: Int,
            imageQueryParams: ImageQueryParams,
        ): Flow<ApiResult<ExhibitionDetail>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    Log.d("ExhibitDetail", "RepositoryImpl")

                    val baseResponse =
                        dataSource.getExhibitDetail(exhibitId, imageQueryParams)

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

                    val exhibitDetail: ExhibitionDetail = dto.toDomain()

                    emit(ApiResult.Success(exhibitDetail))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }
    }
