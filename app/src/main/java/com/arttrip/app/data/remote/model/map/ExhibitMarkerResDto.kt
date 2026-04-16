package com.arttrip.app.data.remote.model.map

data class ExhibitMarkerResDto(
    val markers: List<MarkerItem>,
)

data class MarkerItem(
    val id: Long,
    val lat: Double,
    val lng: Double,
)
