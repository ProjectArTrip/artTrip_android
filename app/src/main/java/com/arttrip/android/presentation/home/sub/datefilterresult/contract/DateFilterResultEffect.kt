package com.arttrip.android.presentation.home.sub.datefilterresult.contract

sealed interface DateFilterResultEffect {
    data object NavigateBack : DateFilterResultEffect
}
