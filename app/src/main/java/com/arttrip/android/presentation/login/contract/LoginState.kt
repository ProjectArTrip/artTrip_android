package com.arttrip.android.presentation.login.contract

data class LoginState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)