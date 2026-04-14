package com.arttrip.android.core.model.enums.foreign

import com.google.android.gms.maps.model.LatLng

enum class ForeignCountry(
    val label: String,
    val latLng: LatLng? = null,
) {
    Entire("전체"),
    France("프랑스", LatLng(48.8566, 2.3522)),
    Germany("독일", LatLng(52.5200, 13.4050)),
    Italy("이탈리아", LatLng(41.9028, 12.4964)),
    Usa("미국", LatLng(38.8951, -77.0369)),
    Austria("오스트리아", LatLng(48.2082, 16.3738)),
    Japan("일본", LatLng(35.6762, 139.6503)),
}
