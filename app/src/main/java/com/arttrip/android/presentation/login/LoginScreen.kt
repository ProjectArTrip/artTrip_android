package com.arttrip.android.presentation.login

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(
    innerPadding: PaddingValues,
    onLoginSuccess: () -> Unit,
) {
    // TODO: 실제 로그인 UI
    Button(
        onClick = onLoginSuccess,
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        Text(text = "로그인 성공 처리(임시)")
    }
}
