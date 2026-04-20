package com.arttrip.app.presentation.map.contract

sealed interface MapEffect {
    data class NavigateToExhibitionDetail(val exhibitionId: Int) : MapEffect
}
