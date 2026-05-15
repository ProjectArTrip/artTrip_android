package com.arttrip.app.data.remote.api

import com.arttrip.app.data.remote.api.ApiConstants.MAINTENANCE_PATH
import com.arttrip.app.data.remote.model.maintenance.MaintenanceStatusResDto
import retrofit2.http.GET

interface MaintenanceApi {
    @GET(MAINTENANCE_PATH)
    suspend fun getMaintenanceStatus(): MaintenanceStatusResDto
}
