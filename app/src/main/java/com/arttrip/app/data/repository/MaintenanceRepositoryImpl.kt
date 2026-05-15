package com.arttrip.app.data.repository

import com.arttrip.app.data.remote.datasource.MaintenanceDataSource
import com.arttrip.app.data.remote.mapper.base.toAppError
import com.arttrip.app.data.remote.mapper.maintenance.toDomain
import com.arttrip.app.data.remote.mapper.user.toDomain
import com.arttrip.app.domain.model.maintenance.Maintenance
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.MaintenanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class MaintenanceRepositoryImpl
    @Inject
    constructor(
        private val maintenanceDataSource: MaintenanceDataSource,
    ) : MaintenanceRepository {
        override fun getMaintenanceState(): Flow<ApiResult<Maintenance>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val dto =
                        maintenanceDataSource.getMaintenanceStatus()
                    val result = dto.toDomain()
                    emit(ApiResult.Success(result))
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    emit(ApiResult.Error(t.toAppError()))
                }
            }
    }
