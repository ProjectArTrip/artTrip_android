package com.arttrip.android.presentation.home.sub.notification

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.presentation.home.sub.notification.contract.NotificationIntent

@Composable
fun NotificationScreen(
    innerPadding: PaddingValues,
    onIntent: (NotificationIntent) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Spacer(
                modifier =
                    Modifier
                        .height(16.dp),
            )
            AppTopBar(
                title = "알림",
                leading = {
                    AppIconButton(
                        iconResId = R.drawable.ic_back_24,
                        contentDescription = "Back Button",
                        onIconClick = {
                            onIntent(NotificationIntent.BackClicked)
                        },
                    )
                },
            )
            Spacer(
                modifier =
                    Modifier
                        .height(12.dp),
            )
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                repeat(10) {
                    NotificationItem(
                        isRead = false,
                        title = "알림 타이틀",
                        message = "알림 내용",
                    )
                }
                repeat(10) {
                    NotificationItem(
                        isRead = true,
                        title = "알림 타이틀",
                        message = "알림 내용 알림 내용 알림 내용 알림 내용 알림 내용 알림 내용 알림 내용 알림 내용 알림 내용 알림 내용 알림 내용 알림 내용 알림 내용 알림 내용 알림 내용 ",
                    )
                }
                Spacer(
                    modifier =
                        Modifier
                            .height(28.dp),
                )
            }
        }
    }
}

@Composable
fun NotificationItem(
    isRead: Boolean,
    title: String,
    message: String,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Spacer(
            modifier =
                Modifier
                    .height(12.dp),
        )
        Row {
            Spacer(
                modifier =
                    Modifier
                        .width(12.dp),
            )
            Icon(
                painter = painterResource(if (isRead) R.drawable.ic_alert_24 else R.drawable.ic_alert_badge_24),
                contentDescription = "Alert Icon",
                tint = Color.Unspecified,
            )
            Spacer(
                modifier =
                    Modifier
                        .width(20.dp),
            )
            Column {
                Spacer(
                    modifier =
                        Modifier
                            .height(1.dp),
                )
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = title,
                        style = AppTextStyle.Title02Bold,
                        color = AppColor.TextPrimary,
                    )
                    Text(
                        text = "N분 전",
                        style = AppTextStyle.Body02Light,
                        color = AppColor.TextTertiary,
                    )
                }
                Spacer(
                    modifier =
                        Modifier
                            .height(8.dp),
                )
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                ) {
                    Text(
                        modifier =
                            Modifier
                                .height(40.dp),
                        text = message,
                        style = AppTextStyle.Body01Regular,
                        color = AppColor.TextSecondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
