package com.arttrip.app.data.remote.datasource

import com.arttrip.app.data.remote.api.MaintenanceApi
import javax.inject.Inject

class MaintenanceDataSource
    @Inject
    constructor(
        private val api: MaintenanceApi,
    ) {
        suspend fun getMaintenanceStatus() = api.getMaintenanceStatus()
    }
