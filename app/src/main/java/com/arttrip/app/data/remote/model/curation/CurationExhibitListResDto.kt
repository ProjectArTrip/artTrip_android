package com.arttrip.app.data.remote.model.curation

import com.arttrip.app.data.remote.model.home.ForeignExhibitResponseDto

data class CurationExhibitListResDto (
    val title: String,
    val exhibits: List<ForeignExhibitResponseDto> = emptyList(),
    val nextCursor: Int?,
    val hasNext: Boolean,
)