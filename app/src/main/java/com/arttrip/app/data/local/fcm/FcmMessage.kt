package com.arttrip.app.data.local.fcm

import com.arttrip.app.core.model.enums.notification.Action

data class FcmMessage(
    val title: String,
    val body: String,
    val action: Action? = null,
    val referenceId: Int? = null,
)
