package com.arttrip.android.presentation.login

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.login.contract.LoginEffect

@Composable
fun LoginRoute(
    innerPadding: PaddingValues,
    onLoginSuccess: (Boolean) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.NavigateToIntro -> {
                    // TODO 인트로 화면 네비게이션
                    onLoginSuccess(true)
                }
                LoginEffect.NavigateToHome -> {
                    onLoginSuccess(false)
                }
                is LoginEffect.ShowError -> {
                    // 토스트/스낵바 등 표시
                }
            }
        }
    }

    LoginScreen(
        state = state,
        innerPadding = innerPadding,
    )
}
