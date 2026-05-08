package com.arttrip.app.data.local.fcm

data class FcmMessage(
    val title: String,
    val body: String,
    val action: String? = null,
    val exhibitId: Int? = null,
)
