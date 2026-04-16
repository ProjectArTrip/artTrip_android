package com.arttrip.app.presentation.map.contract

import com.arttrip.app.domain.model.map.ExhibitionMarker
import com.arttrip.app.domain.model.network.ApiError
import com.google.android.gms.maps.model.LatLng

data class MapState(
    val markers: List<ExhibitionMarker> = emptyList(),
    val isLoading: Boolean = false,
    val error: ApiError? = null,
    val selectedClusterCount: Int = 0,
    val currentLocation: LatLng? = null,
)
