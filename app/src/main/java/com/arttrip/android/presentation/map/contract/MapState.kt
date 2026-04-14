package com.arttrip.android.presentation.map.contract

import com.arttrip.android.domain.model.map.ExhibitionMarker
import com.arttrip.android.domain.model.network.ApiError
import com.google.android.gms.maps.model.LatLng

data class MapState(
    val markers: List<ExhibitionMarker> = emptyList(),
    val isLoading: Boolean = false,
    val error: ApiError? = null,
    val selectedClusterCount: Int = 0,
    val currentLocation: LatLng? = null,
)
