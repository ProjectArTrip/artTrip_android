package com.arttrip.android.data.repository

import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.data.remote.datasource.ExhibitDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.exhibit.toDomain
import com.arttrip.android.domain.model.exhibition.ExhibitionDetail
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
                    val dto =
                        dataSource.getExhibitDetail(exhibitId, imageQueryParams)
                    val exhibitDetail: ExhibitionDetail = dto.toDomain()

                    emit(ApiResult.Success(exhibitDetail))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }
    }
