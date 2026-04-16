package com.arttrip.app.presentation.mypage.sub.settings.sub.notification

import AppToggle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.app.R
import com.arttrip.app.core.ui.component.appbar.AppTopBar
import com.arttrip.app.core.ui.component.button.AppIconButton
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.presentation.mypage.sub.settings.sub.notification.contract.NotificationIntent
import com.arttrip.app.presentation.mypage.sub.settings.sub.notification.contract.NotificationState

private val CONTENT_HORIZONTAL_PADDING = 24.dp
private val BOTTOM_SCROLL_SPACER = 48.dp

@Composable
fun NotificationScreen(
    innerPadding: PaddingValues,
    state: NotificationState,
    onIntent: (NotificationIntent) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(AppColor.Gray0)
                .padding(innerPadding),
    ) {
        AppTopBar(
            title = "알림설정",
            leading = {
                AppIconButton(
                    iconResId = R.drawable.ic_back_24,
                    contentDescription = "뒤로가기",
                    onIconClick = {
                        onIntent(NotificationIntent.BackClicked)
                    },
                )
            },
        )
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CONTENT_HORIZONTAL_PADDING)
                    .verticalScroll(scrollState),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            NotificationSection(
                title = "활동 알림",
                description = "전시 오픈, 스탬프 발급 등 주요 활동 소식을 푸시로 받아볼 수 있어요.",
            ) {
                ToggleItem(
                    title = "전시 정보",
                    checked = state.exhibitionInfoEnabled,
                    onCheckedChange = { onIntent(NotificationIntent.ExhibitionInfoToggled(it)) },
                )
                ToggleItem(
                    title = "저장한 전시 오픈",
                    checked = state.savedExhibitionOpenEnabled,
                    onCheckedChange = { onIntent(NotificationIntent.SavedExhibitionOpenToggled(it)) },
                )
                ToggleItem(
                    title = "스탬프 발급",
                    checked = state.stampIssuedEnabled,
                    onCheckedChange = { onIntent(NotificationIntent.StampIssuedToggled(it)) },
                )
            }
            Spacer(modifier = Modifier.height(56.dp))
            NotificationSection(
                title = "광고성 정보 수신",
                description = "이벤트 등 유용한 소식을 앱 푸시로 안내 받을 수 있어요.",
            ) {
                ToggleItem(
                    title = "앱 푸시",
                    checked = state.marketingPushEnabled,
                    onCheckedChange = { onIntent(NotificationIntent.MarketingPushToggled(it)) },
                )
            }
            Spacer(modifier = Modifier.height(BOTTOM_SCROLL_SPACER))
        }
    }
}

@Composable
private fun NotificationSection(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = description,
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextTertiary,
        )
        Spacer(Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            content()
        }
    }
}

@Composable
private fun ToggleItem(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = AppTextStyle.Title02Light,
            color = AppColor.TextPrimary,
        )
        AppToggle(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = true,
        )
    }
}
