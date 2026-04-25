package com.arttrip.app.data.repository

import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.data.remote.datasource.CurationDataSource
import com.arttrip.app.data.remote.mapper.base.toAppError
import com.arttrip.app.data.remote.mapper.curation.toDomain
import com.arttrip.app.domain.model.curation.Curation
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
    }