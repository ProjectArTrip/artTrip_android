package com.arttrip.app.data.remote.mapper.map

import com.arttrip.app.data.remote.mapper.exhibit.toDomain
import com.arttrip.app.data.remote.model.home.ExhibitListPageResDto
import com.arttrip.app.data.remote.model.map.ExhibitMarkerResDto
import com.arttrip.app.data.remote.model.map.MarkerItem
import com.arttrip.app.domain.model.map.ClusterExhibitPage
import com.arttrip.app.domain.model.map.ExhibitionMarker
import com.google.android.gms.maps.model.LatLng

fun ExhibitMarkerResDto.toDomain(): List<ExhibitionMarker> = markers.map { it.toDomain() }

fun MarkerItem.toDomain(): ExhibitionMarker =
    ExhibitionMarker(
        id = id,
        latLng = LatLng(lat, lng),
    )

fun ExhibitListPageResDto.toClusterExhibitPage(): ClusterExhibitPage =
    ClusterExhibitPage(
        exhibits = exhibits.map { it.toDomain() },
        nextCursor = nextCursor,
        hasNext = hasNext,
        totalCount = exhibitTotalCount,
    )
