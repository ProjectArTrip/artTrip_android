package com.arttrip.android.data.remote.model.map

data class ExhibitMarkerResDto(
    val markers: List<MarkerItem>
)

data class MarkerItem(
    val id: Long,
    val lat: Double,
    val lng: Double
)