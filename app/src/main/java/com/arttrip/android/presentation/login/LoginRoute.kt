package com.arttrip.android.presentation.login

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

@Composable
fun LoginRoute(
    innerPadding: PaddingValues,
    onLoginSuccess: () -> Unit,
) {
    LoginScreen(
        innerPadding = innerPadding,
        onLoginSuccess = onLoginSuccess,
    )
}
