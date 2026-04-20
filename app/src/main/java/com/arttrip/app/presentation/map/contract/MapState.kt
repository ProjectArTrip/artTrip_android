package com.arttrip.app.presentation.map.contract

import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.domain.model.map.ExhibitionMarker
import com.arttrip.app.domain.model.network.ApiError
import com.google.android.gms.maps.model.LatLng

private val SEOUL = LatLng(37.5665, 126.9780)

data class MapState(
    val markers: List<ExhibitionMarker> = emptyList(),
    val isLoading: Boolean = false,
    val error: ApiError? = null,
    val selectedClusterCount: Int = 0,
    val currentLocation: LatLng? = null,
    val cameraLatLng: LatLng = SEOUL,
    val cameraZoom: Float = 15f,
    val selectedCountry: ForeignCountry? = null,
    val hasCenteredOnLocation: Boolean = false,
    val selectedIds: List<Int> = emptyList(),
)
