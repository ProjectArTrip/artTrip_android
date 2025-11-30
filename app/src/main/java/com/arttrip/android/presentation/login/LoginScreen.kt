package com.arttrip.android.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.button.SocialLoginButton
import com.arttrip.android.core.ui.component.button.SocialLoginProvider
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.ArtTripTheme
import com.arttrip.android.presentation.login.contract.LoginState

@Composable
fun LoginScreen(
    innerPadding: PaddingValues,
    state: LoginState,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = AppColor.Primary300)
                .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_logo_white),
            tint = Color.Unspecified,
            contentDescription = "logo",
        )
        Spacer(modifier = Modifier.height(143.dp))

        SocialLoginButton(
            provider = SocialLoginProvider.Kakao,
            onClick = {},
        )
        Spacer(modifier = Modifier.height(12.dp))
        SocialLoginButton(
            provider = SocialLoginProvider.Google,
            onClick = {},
            enabled = false,
        )
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
            state = LoginState(false),
            innerPadding = PaddingValues(0.dp),
        )
    }
}
