package com.arttrip.app.data.remote.model.maintenance

data class MaintenanceStatusResDto(
    val configured: Boolean,
    val active: Boolean,
    val state: String,
    val title: String?,
    val message: String?,
    val startAt: String?,
    val endAt: String?,
    val buttonText: String?,
    val forceExit: Boolean?,
    val refreshAfterSeconds: Int?,
    val version: Int?,
)
