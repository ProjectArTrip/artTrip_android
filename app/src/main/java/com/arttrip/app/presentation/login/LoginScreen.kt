package com.arttrip.app.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.app.R
import com.arttrip.app.core.ui.component.button.SocialLoginButton
import com.arttrip.app.core.ui.component.button.SocialLoginProvider
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.core.ui.theme.ArtTripTheme
import com.arttrip.app.presentation.login.contract.LoginIntent
import com.arttrip.app.presentation.login.contract.LoginState

@Composable
fun LoginScreen(
    innerPadding: PaddingValues,
    state: LoginState,
    onIntent: (LoginIntent) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = AppColor.Primary300)
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(266.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_logo_white),
            tint = Color.Unspecified,
            contentDescription = "logo",
        )
        Spacer(modifier = Modifier.weight(1f))

        SocialLoginButton(
            provider = SocialLoginProvider.Kakao,
            onClick = { onIntent(LoginIntent.ClickKakaoLogin) },
            enabled = !state.isLoading,
        )
        Spacer(modifier = Modifier.height(12.dp))
        SocialLoginButton(
            provider = SocialLoginProvider.Google,
            onClick = {
                onIntent(LoginIntent.ClickGoogleLogin)
            },
            enabled = !state.isLoading,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text =
                "회원가입 시 아트트립 서비스 필수 동의 항목인\n" +
                    "개인정보처리방침과 서비스이용약관에 동의한 것으로 간주합니다.",
            style = AppTextStyle.Body03Regular,
            color = AppColor.TextWhite,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(34.dp))
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "LoginScreen Preview",
)
@Composable
fun PreviewLoginScreen() {
    ArtTripTheme {
        LoginScreen(
            innerPadding = PaddingValues(0.dp),
            state =
                LoginState(
                    isLoading = false,
                    errorMessage = null,
                ),
            onIntent = { /* no-op for preview */ },
        )
    }
}
