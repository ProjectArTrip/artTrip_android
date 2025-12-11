package com.arttrip.android.presentation.home.contract

sealed interface HomeEffect {
    object NavigateToDateFilter : HomeEffect
}
