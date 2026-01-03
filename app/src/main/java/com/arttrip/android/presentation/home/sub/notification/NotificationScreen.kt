package com.arttrip.android.presentation.home.sub.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle

@Composable
fun NotificationScreen(innerPadding: PaddingValues) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.Gray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(color = Color.Blue),
                contentAlignment = Alignment.Center
            ) {
                Text("앱바 영역")
            }
        }
    }
}

@Composable
fun NotificationItem(isRead: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .height(12.dp)
        )
        Row {
            Icon(
                painter = painterResource(if (isRead) R.drawable.ic_alert_24 else R.drawable.ic_alert_badge_24),
                contentDescription = "Alert Icon"
            )
            Spacer(
                modifier = Modifier
                    .width(20.dp)
            )
            Column {
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                )
                Text(
                    text = "알림 타이틀",
                    style = AppTextStyle.Title02Bold,
                    color = AppColor.TextPrimary
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                Text("")
            }
        }
    }
}