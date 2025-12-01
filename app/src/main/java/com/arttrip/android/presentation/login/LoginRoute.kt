package com.arttrip.android.presentation.login

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.navigation.AppRoute
import com.arttrip.android.core.navigation.BottomNavItem
import com.arttrip.android.presentation.login.contract.LoginEffect
import com.arttrip.android.presentation.login.contract.LoginIntent
import com.kakao.sdk.user.UserApiClient

@Composable
fun LoginRoute(
    innerPadding: PaddingValues,
    onLoginSuccess: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.LaunchKakaoLogin -> {
                    val activity = context as? Activity ?: return@collect
                    // TODO 앱에서 카카오톡 설치여부 판별할지 아래처럼 쓸지
                    UserApiClient.instance.loginWithKakao(activity) { token, error ->

                        if (error != null) {
                            viewModel.onIntent(LoginIntent.KakaoLoginFailure(error))
                            return@loginWithKakao
                        }

                        if (token == null) {
                            viewModel.onIntent(
                                LoginIntent.KakaoLoginFailure(
                                    IllegalStateException("Kakao login token is null"),
                                ),
                            )
                            return@loginWithKakao
                        }

                        val idToken = token.idToken
                        if (idToken.isNullOrBlank()) {
                            viewModel.onIntent(
                                LoginIntent.KakaoLoginFailure(
                                    IllegalStateException("Kakao login idToken is null or blank"),
                                ),
                            )
                            return@loginWithKakao
                        }

                        viewModel.onIntent(LoginIntent.KakaoLoginSuccess(idToken))
                    }
                }

                LoginEffect.NavigateToIntro -> {
                    onLoginSuccess(AppRoute.INTRO)
                }
                LoginEffect.NavigateToHome -> {
                    onLoginSuccess(BottomNavItem.Home.route)
                }
                is LoginEffect.ShowError -> {
                    // 토스트/스낵바 등 표시
                }
            }
        }
    }

    LoginScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
