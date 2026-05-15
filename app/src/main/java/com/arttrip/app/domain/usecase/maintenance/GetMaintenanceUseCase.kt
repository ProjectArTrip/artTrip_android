package com.arttrip.app.domain.usecase.maintenance

import com.arttrip.app.domain.model.maintenance.Maintenance
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.MaintenanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMaintenanceUseCase
    @Inject
    constructor(
        private val maintenanceRepository: MaintenanceRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<Maintenance>> = maintenanceRepository.getMaintenanceState()
    }
