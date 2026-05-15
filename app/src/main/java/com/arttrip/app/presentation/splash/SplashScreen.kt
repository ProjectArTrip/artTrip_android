package com.arttrip.app.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arttrip.app.R
import com.arttrip.app.core.ui.component.dialog.AppSingleButtonDialog
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.presentation.splash.contract.SplashIntent
import com.arttrip.app.presentation.splash.contract.SplashState

@Composable
fun SplashScreen(
    state: SplashState,
    onIntent: (SplashIntent) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = AppColor.Primary300),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier =
                    Modifier
                        .width(188.dp)
                        .height(60.dp),
                painter = painterResource(R.drawable.ic_logo_white),
                contentDescription = "logo image",
            )
            Spacer(
                modifier =
                    Modifier
                        .height(20.dp),
            )
            Text(
                text = "세상의 전시, 내 손 안에.",
                style = AppTextStyle.Title02Light,
                color = AppColor.TextWhite,
            )
        }
    }
    AppSingleButtonDialog(
        visible = state.showMaintenanceDialog,
        onDismissRequest = {},
        primaryText = "종료",
        onPrimaryClick = { onIntent(SplashIntent.ExitApp) },
        dismissible = false,
    ) {
        Text(
            "📢 서버 점검 안내",
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(Modifier.height(11.dp))
        HorizontalDivider(thickness = 1.dp, color = AppColor.Gray50)
        Spacer(Modifier.height(24.dp))

        Text(
            "보다 안정적인 서비스 제공을 위해\n현재 서버 점검이 진행 중입니다.",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
            textAlign = TextAlign.Center,
        )
        if (state.startAt != null && state.endAt != null) {
            Spacer(Modifier.height(16.dp))
            Text(
                "${state.startAt} ~ ${state.endAt}",
                style = AppTextStyle.Body01Bold,
                color = AppColor.SubRed,
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}
