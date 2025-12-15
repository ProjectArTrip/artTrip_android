package com.arttrip.android.domain.model.home

enum class ExhibitStatus {
    ONGOING,
    UPCOMING,
    DEADLINE
}

data class ExhibitModel(
    val id: Int,
    val title: String,
    val posterUrl: String = "",
    val status: ExhibitStatus,
    val exhibitPeriod: String,
)
