package com.arttrip.android.domain.model.home

enum class ExhibitStatus {
    ONGOING,
    UPCOMING,
    ENDING_SOON,
    FINISHED
}

data class ExhibitModel(
    val id: Int,
    val title: String,
    val posterUrl: String = "",
    val status: ExhibitStatus,
    val exhibitPeriod: String,
)
