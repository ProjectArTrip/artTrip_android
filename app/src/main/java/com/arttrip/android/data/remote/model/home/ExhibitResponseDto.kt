package com.arttrip.android.data.remote.model.home

import kotlinx.serialization.SerialName

data class ExhibitDto(
    @SerialName("exhibit_id")
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val status: String,
    val exhibitPeriod: String,
)
