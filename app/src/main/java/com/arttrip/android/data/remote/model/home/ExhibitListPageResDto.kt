package com.arttrip.android.data.remote.model.home

data class ExhibitListPageResDto(
    val exhibits: List<ForeignExhibitResponseDto> = emptyList(),
    val nextCursor: Int?,
    val hasNext: Boolean,
)