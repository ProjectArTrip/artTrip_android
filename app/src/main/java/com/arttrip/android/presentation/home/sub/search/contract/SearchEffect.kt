package com.arttrip.android.presentation.home.sub.search.contract

sealed interface SearchEffect {
    object NavigateBack : SearchEffect
    data class NavigateToDetail(val exhibitId: Int) : SearchEffect
}
