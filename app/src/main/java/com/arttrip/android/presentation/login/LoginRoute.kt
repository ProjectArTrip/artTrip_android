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
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
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

                    launchKakaoLogin(
                        activity = activity,
                        onSuccess = { idToken ->
                            viewModel.onIntent(LoginIntent.KakaoLoginSuccess(idToken))
                        },
                        onFailure = { throwable ->
                            viewModel.onIntent(LoginIntent.KakaoLoginFailure(throwable))
                        },
                    )
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

private fun launchKakaoLogin(
    activity: Activity,
    onSuccess: (String) -> Unit,
    onFailure: (Throwable) -> Unit,
) {
    // 공통: 카카오계정 로그인 콜백
    val accountCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            onFailure(error)
        } else if (token == null) {
            onFailure(IllegalStateException("Kakao login token is null"))
        } else {
            val idToken = token.idToken
            if (idToken.isNullOrBlank()) {
                onFailure(IllegalStateException("Kakao login idToken is null or blank"))
            } else {
                onSuccess(idToken)
            }
        }
    }

    if (UserApiClient.instance.isKakaoTalkLoginAvailable(activity)) {
        UserApiClient.instance.loginWithKakaoTalk(activity) { token, error ->

            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    onFailure(error)
                    return@loginWithKakaoTalk
                }

                UserApiClient.instance.loginWithKakaoAccount(activity, callback = accountCallback)
                return@loginWithKakaoTalk
            }

            if (token == null) {
                onFailure(IllegalStateException("Kakao login token is null"))
                return@loginWithKakaoTalk
            }

            val idToken = token.idToken
            if (idToken.isNullOrBlank()) {
                onFailure(IllegalStateException("Kakao login idToken is null or blank"))
                return@loginWithKakaoTalk
            }

            onSuccess(idToken)
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(activity, callback = accountCallback)
    }
}
