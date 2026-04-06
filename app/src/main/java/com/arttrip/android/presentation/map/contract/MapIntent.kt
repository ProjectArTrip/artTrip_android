package com.arttrip.android.presentation.map.contract

sealed interface MapIntent {
    data class LoadMarkers(val etag: String) : MapIntent
    data class OnClusterClicked(val count: Int, val ids: List<Int>) : MapIntent
    data class OnCameraIdle(val visibleCount: Int, val ids: List<Int>) : MapIntent
}
