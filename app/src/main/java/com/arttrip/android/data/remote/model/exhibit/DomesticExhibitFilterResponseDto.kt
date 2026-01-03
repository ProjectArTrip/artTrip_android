package com.arttrip.android.data.remote.model.exhibit

import com.arttrip.android.data.remote.model.home.DomesticExhibitResponseDto

data class DomesticExhibitFilterResponseDto(
    val exhibits: List<DomesticExhibitResponseDto>,
    val hasNext: Boolean,
    val nextCursor: Int?
)