package com.arttrip.android.data.remote.model.user

data class UserRecentExhibitsResDto(
    val exhibits: List<RecentExhibitDto>,
)

data class RecentExhibitDto(
    val exhibitId: Int,
    val title: String,
    val exhibitHallName: String,
    val exhibitImage: String,
)
