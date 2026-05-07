package com.arttrip.app.presentation.home.sub.curation.contract

sealed interface CurationIntent {
    data class Initialize(
        val curationId: Long,
    ) : CurationIntent

    object BackClicked : CurationIntent

    object NotificationIconClicked : CurationIntent

    data class ExhibitionClicked(
        val id: Int,
    ) : CurationIntent

    data class LikeClicked(
        val id: Int,
    ) : CurationIntent
}
