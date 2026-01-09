package com.arttrip.android.presentation.my.contract

sealed interface MyPageEffect {
    data object NavigateToRecentExhibitions : MyPageEffect

    data object NavigateToMyReviews : MyPageEffect

    data object NavigateToTasteAnalysis : MyPageEffect

    data object NavigateToSettings : MyPageEffect

    data object NavigateToEditProfile : MyPageEffect
}
