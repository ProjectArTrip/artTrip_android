package com.arttrip.app.presentation.intro.nickname.contract

sealed interface NicknameIntent {
    data class NicknameChanged(
        val value: String,
    ) : NicknameIntent

    data object NicknameConfirmClicked : NicknameIntent
}
