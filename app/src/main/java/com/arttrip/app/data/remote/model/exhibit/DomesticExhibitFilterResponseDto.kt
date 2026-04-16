package com.arttrip.app.data.remote.model.exhibit

import com.arttrip.app.data.remote.model.home.DomesticExhibitResponseDto

data class DomesticExhibitFilterResponseDto(
    val exhibits: List<DomesticExhibitResponseDto>,
    val hasNext: Boolean,
    val nextCursor: Int?,
)
