package com.arttrip.app.presentation.intro.nickname.contract

data class NicknameState(
    val isLoading: Boolean = false,
    val nicknameInput: String = "",
    val helperText: String? = null,
)
