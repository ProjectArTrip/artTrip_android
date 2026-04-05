package com.arttrip.android.data.remote.mapper.map

import com.arttrip.android.data.remote.model.map.ExhibitMarkerResDto
import com.arttrip.android.data.remote.model.map.MarkerItem
import com.arttrip.android.domain.model.map.ExhibitionMarker
import com.google.android.gms.maps.model.LatLng

fun ExhibitMarkerResDto.toDomain(): List<ExhibitionMarker> = markers.map { it.toDomain() }

fun MarkerItem.toDomain(): ExhibitionMarker =
    ExhibitionMarker(
        id = id,
        latLng = LatLng(lat, lng),
    )
