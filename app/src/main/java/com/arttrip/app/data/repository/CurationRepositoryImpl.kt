package com.arttrip.app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.data.remote.datasource.CurationDataSource
import com.arttrip.app.data.remote.mapper.base.toAppError
import com.arttrip.app.data.remote.mapper.curation.toDomain
import com.arttrip.app.data.remote.paging.curation.CurationExhibitPagingSource
import com.arttrip.app.domain.model.curation.Curation
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.CurationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurationRepositoryImpl
    @Inject
    constructor(
        private val dataSource: CurationDataSource,
    ) : CurationRepository {
        override fun getForeignCurations(country: ForeignCountry): Flow<ApiResult<Curation>> =
            flow {
                try {
                    val dto = dataSource.getForeignCurations(country = country.label)
                    emit(ApiResult.Success(dto.toDomain()))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getDomesticCurations(): Flow<ApiResult<Curation>> =
            flow {
                try {
                    val dto = dataSource.getDomesticCurations()
                    emit(ApiResult.Success(dto.toDomain()))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getCurationExhibits(
            curationId: Long,
            pageSize: Int,
            initialLoadSize: Int,
            onTitleLoaded: (String) -> Unit,
        ): Flow<PagingData<Exhibition>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = pageSize,
                        initialLoadSize = initialLoadSize,
                        prefetchDistance = 1,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    CurationExhibitPagingSource(
                        dataSource = dataSource,
                        curationId = curationId,
                        onTitleLoaded = onTitleLoaded,
                    )
                },
            ).flow
    }
