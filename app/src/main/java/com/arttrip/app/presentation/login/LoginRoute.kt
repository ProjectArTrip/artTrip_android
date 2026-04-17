package com.arttrip.app.presentation.login

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.app.BuildConfig
import com.arttrip.app.core.navigation.app.AppRoute
import com.arttrip.app.core.util.LocalToastController
import com.arttrip.app.presentation.login.contract.LoginEffect
import com.arttrip.app.presentation.login.contract.LoginIntent
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Composable
fun LoginRoute(
    innerPadding: PaddingValues,
    onNavigate: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val toast = LocalToastController.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.LaunchKakaoLogin -> {
                    val activity = context as? Activity ?: return@collect
                    try {
                        val idToken = launchKakaoLogin(activity)
                        viewModel.onIntent(LoginIntent.KakaoLoginSuccess(idToken))
                    } catch (e: ClientError) {
                        if (e.reason == ClientErrorCause.Cancelled) {
                            viewModel.onIntent(
                                LoginIntent.KakaoLoginFailure(
                                    null,
                                ),
                            )
                        } else {
                            viewModel.onIntent(LoginIntent.KakaoLoginFailure(e))
                        }
                    } catch (t: Throwable) {
                        viewModel.onIntent(LoginIntent.KakaoLoginFailure(t))
                    }
                }
                LoginEffect.LaunchGoogleLogin -> {
                    val activity = context as? Activity ?: return@collect
                    val webClientId = BuildConfig.GOOGLE_WEB_CLIENT_ID

                    try {
                        val idToken =
                            launchGoogleLogin(
                                activity = activity,
                                webClientId = webClientId,
                            )
                        viewModel.onIntent(LoginIntent.GoogleLoginSuccess(idToken))
                    } catch (e: GetCredentialCancellationException) {
                        // 유저 취소
                        viewModel.onIntent(
                            LoginIntent.GoogleLoginFailure(
                                null,
                            ),
                        )
                    } catch (t: Throwable) {
                        viewModel.onIntent(LoginIntent.GoogleLoginFailure(t))
                    }
                }

                LoginEffect.NavigateToIntro -> {
                    onNavigate(AppRoute.INTRO_TASTE)
                }
                LoginEffect.NavigateToHome -> {
                    onNavigate(AppRoute.MAIN)
                }
                is LoginEffect.ShowError -> toast.show(effect.message)
            }
        }
    }

    LoginScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}

private suspend fun launchKakaoLogin(activity: Activity): String =
    suspendCancellableCoroutine { cont ->

        fun resumeFailure(t: Throwable) {
            if (cont.isActive) cont.resumeWithException(t)
        }

        fun resumeSuccess(token: OAuthToken) {
            val idToken = token.idToken
            if (idToken.isNullOrBlank()) {
                resumeFailure(IllegalStateException("Kakao login idToken is null or blank"))
            } else if (cont.isActive) {
                cont.resume(idToken)
            }
        }

        val accountCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            when {
                error != null -> resumeFailure(error)
                token == null -> resumeFailure(IllegalStateException("Kakao login token is null"))
                else -> resumeSuccess(token)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(activity)) {
            UserApiClient.instance.loginWithKakaoTalk(activity) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        resumeFailure(error)
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(activity, callback = accountCallback)
                    return@loginWithKakaoTalk
                }

                if (token == null) {
                    resumeFailure(IllegalStateException("Kakao login token is null"))
                    return@loginWithKakaoTalk
                }

                resumeSuccess(token)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(activity, callback = accountCallback)
        }
    }

private suspend fun launchGoogleLogin(
    activity: Activity,
    webClientId: String,
): String {
    val credentialManager = CredentialManager.create(activity)

    val googleOption =
        GetSignInWithGoogleOption
            .Builder(webClientId)
            .build()

    val request =
        GetCredentialRequest
            .Builder()
            .addCredentialOption(googleOption)
            .build()

    val result =
        credentialManager.getCredential(
            request = request,
            context = activity,
        )

    val credential = result.credential
    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
    return googleIdTokenCredential.idToken
}
