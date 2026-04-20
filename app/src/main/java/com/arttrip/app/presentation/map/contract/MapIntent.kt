package com.arttrip.app.presentation.map.contract

sealed interface MapIntent {
    data class LoadMarkers(
        val etag: String,
    ) : MapIntent

    data class OnClusterClicked(
        val count: Int,
        val ids: List<Int>,
    ) : MapIntent

    data class OnCameraIdle(
        val visibleCount: Int,
        val ids: List<Int>,
    ) : MapIntent

    data object OnLocationPermissionGranted : MapIntent

    data object OnLocationPermissionDenied : MapIntent

    data class ExhibitionClicked(val id: Int) : MapIntent

    data class OnCameraMoved(val latLng: com.google.android.gms.maps.model.LatLng, val zoom: Float) : MapIntent

    data class OnCountrySelected(val country: com.arttrip.app.core.model.enums.foreign.ForeignCountry?) : MapIntent

    data object OnLocationCentered : MapIntent
}
