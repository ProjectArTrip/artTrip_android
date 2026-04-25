package com.arttrip.app.data.remote.model.curation

import com.arttrip.app.data.remote.model.home.ForeignExhibitResponseDto

data class CurationResponse(
    val curationId: Long,
    val title: String,
    val subtitle: String,
    val exhibits: List<ForeignExhibitResponseDto>
)