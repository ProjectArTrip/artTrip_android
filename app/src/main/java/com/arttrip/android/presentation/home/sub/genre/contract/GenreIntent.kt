package com.arttrip.android.presentation.home.sub.genre.contract

sealed interface GenreIntent {
    object BackClicked : GenreIntent
}
