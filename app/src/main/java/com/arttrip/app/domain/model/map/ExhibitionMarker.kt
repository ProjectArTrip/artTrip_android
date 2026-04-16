package com.arttrip.app.domain.model.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class ExhibitionMarker(
    val id: Long,
    val latLng: LatLng,
) : ClusterItem {
    override fun getPosition(): LatLng = latLng

    override fun getTitle(): String = ""

    override fun getSnippet(): String = ""

    override fun getZIndex(): Float? = null
}
