package com.arttrip.app.domain.repository

import com.arttrip.app.domain.model.maintenance.Maintenance
import com.arttrip.app.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface MaintenanceRepository {
    fun getMaintenanceState(): Flow<ApiResult<Maintenance>>
}
