package com.arttrip.app.presentation.bookmark.contract

sealed interface BookmarkEffect {
    data class NavigateToDetail(
        val exhibitId: Int,
    ) : BookmarkEffect

    data class ShowToast(
        val message: String,
    ) : BookmarkEffect
}
