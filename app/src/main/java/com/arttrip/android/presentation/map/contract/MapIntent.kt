package com.arttrip.android.presentation.map.contract

sealed interface MapIntent {
    data class LoadMarkers(val etag: String) : MapIntent
    data class OnClusterClicked(val count: Int) : MapIntent
    data class OnCameraIdle(val visibleCount: Int) : MapIntent
}
