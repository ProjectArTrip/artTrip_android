package com.arttrip.android.presentation.map.contract

sealed interface MapIntent {
    data class LoadMarkers(val etag: String) : MapIntent
}
