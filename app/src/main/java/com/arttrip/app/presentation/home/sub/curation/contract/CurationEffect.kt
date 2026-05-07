package com.arttrip.app.presentation.home.sub.curation.contract

sealed interface CurationEffect {
    object NavigateBack : CurationEffect

    object NavigateToNotification : CurationEffect

    data class NavigateToDetail(
        val exhibitId: Int,
    ) : CurationEffect
}
