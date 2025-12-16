package com.arttrip.android.presentation.home.contract

sealed interface HomeEffect {
    object NavigateToAlert : HomeEffect

    object NavigateToDateFilter : HomeEffect

    object NavigateToSearch : HomeEffect
}
