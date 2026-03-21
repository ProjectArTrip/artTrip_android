package com.arttrip.android.presentation.home.sub.genre.contract

sealed interface GenreEffect {
    object NavigateBack : GenreEffect
    object NavigateToNotification : GenreEffect
    data class NavigateToDetail(val exhibitId: Int) : GenreEffect
}
