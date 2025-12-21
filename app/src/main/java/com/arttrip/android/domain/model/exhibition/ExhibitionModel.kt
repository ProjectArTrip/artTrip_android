package com.arttrip.android.domain.model.exhibition

import com.arttrip.android.core.model.enum.exhibit.ExhibitStatus

data class ExhibitionModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val status: ExhibitStatus,
    val period: String,
    val hallName: String,
    val place: String,
)
