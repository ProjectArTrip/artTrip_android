package com.arttrip.app.presentation.intro.nickname.contract

sealed interface NicknameEffect {
    data object NavigateToTaste : NicknameEffect

    data class ShowToast(
        val message: String,
    ) : NicknameEffect
}
