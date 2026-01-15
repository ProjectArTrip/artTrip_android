package com.arttrip.android.presentation.my.sub.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.dialog.AppDialog
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.presentation.my.sub.settings.contract.SettingsIntent
import com.arttrip.android.presentation.my.sub.settings.contract.SettingsState

private val CONTENT_HORIZONTAL_PADDING = 24.dp
private val BOTTOM_SCROLL_SPACER = 48.dp

@Composable
fun SettingsScreen(
    innerPadding: PaddingValues,
    state: SettingsState,
    onIntent: (SettingsIntent) -> Unit,
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
            title = "설정",
            leading = {
                AppIconButton(
                    iconResId = R.drawable.ic_back_24,
                    contentDescription = "뒤로가기",
                    onIconClick = {
                        onIntent(SettingsIntent.BackClicked)
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

            SettingsSection(title = "권한 설정") {
                MenuItem(title = "알림 설정") { onIntent(SettingsIntent.NotificationSettingsClick) }
            }
            Spacer(modifier = Modifier.height(56.dp))

            SettingsSection(title = "아트트립 정보") {
                MenuItem(title = "공지사항") { onIntent(SettingsIntent.NoticeClick) }
                MenuItem(title = "개인정보 처리방침") { onIntent(SettingsIntent.PrivacyPolicyClick) }
                MenuItem(title = "서비스 이용약관") { onIntent(SettingsIntent.TermsOfServiceClick) }
                MenuItem(title = "앱 버전", trailing = {
                    Text(
                        text = state.appVersion,
                        style = AppTextStyle.Body01Bold,
                        color = AppColor.TextPrimary,
                    )
                })
                MenuItem(
                    title = "회원 탈퇴",
                    titleColor = AppColor.TextSecondary,
                    trailing = null,
                ) { onIntent(SettingsIntent.DeleteAccountClick) }
            }

            Spacer(modifier = Modifier.height(BOTTOM_SCROLL_SPACER))
            DeleteAccountDialog(
                state.isDeleteAccountDialogVisible,
                { onIntent(SettingsIntent.DeleteAccountDialogDismissed) },
                { onIntent(SettingsIntent.DeleteAccountDialogDismissed) },
            )
        }
    }
}

@Composable
private fun SettingsSection(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier.fillMaxWidth()) {
        Text(
            modifier = modifier,
            text = title,
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            content()
        }
    }
}

@Composable
private fun MenuItem(
    title: String,
    modifier: Modifier = Modifier,
    titleColor: Color = AppColor.TextPrimary,
    trailing: (@Composable () -> Unit)? = {
        Icon(
            painter = painterResource(id = R.drawable.ic_more_24),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    },
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth().height(40.dp).let { if (onClick != null) it.clickable { onClick() } else it },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = AppTextStyle.Title02Light,
            color = titleColor,
        )
        trailing?.invoke()
    }
}

@Composable
private fun DeleteAccountDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmClicked: () -> Unit,
) {
    AppDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        primaryText = "탈퇴하기",
        onPrimaryClick = onConfirmClicked,
        secondaryText = "취소",
        onSecondaryClick = onDismissRequest,
    ) {
        Text(
            text = "회원 탈퇴 안내",
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(11.dp))
        HorizontalDivider(color = AppColor.Gray50, thickness = 1.dp)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text =
                "아트트립 탈퇴시 회원님의 사용정보\n" +
                    "(전시 즐겨찾기 목록, 리뷰, 스탬프 등)는\n" +
                    "모두 삭제 됩니다.",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "탈퇴하시겠습니까?",
            style = AppTextStyle.Body01Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
