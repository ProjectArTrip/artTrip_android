package com.arttrip.app.domain.model.maintenance

data class Maintenance(
    val state: MaintenanceState,
    val startAt: String?,
    val endAt: String?,
)
