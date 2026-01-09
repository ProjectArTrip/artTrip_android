package com.arttrip.android.presentation.my

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.presentation.my.contract.MyPageIntent
import com.arttrip.android.presentation.my.contract.MyPageState

private val CONTENT_HORIZONTAL_PADDING = 24.dp
private val MENU_ITEM_GAP = 24.dp
private val BOTTOM_SCROLL_SPACER = 48.dp

@Composable
fun MyPageScreen(
    innerPadding: PaddingValues,
    state: MyPageState,
    onIntent: (MyPageIntent) -> Unit,
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
            title = "마이페이지",
            leading = null,
            actions = {
                AppIconButton(
                    iconResId = R.drawable.ic_alert_24,
                    onIconClick = {},
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

            ProfileRow(
                name = state.userName,
                profileUrl = state.profileImageUrl,
                onMoreClick = {
                    onIntent(MyPageIntent.ClickProfileMore)
                },
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = AppColor.Gray100, thickness = 1.dp)
            Spacer(modifier = Modifier.height(19.dp))
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MENU_ITEM_GAP),
            ) {
                MenuItem(title = "최근 본 전시", onClick = { onIntent(MyPageIntent.ClickRecentExhibitions) })
                MenuItem(title = "나의 리뷰", onClick = { onIntent(MyPageIntent.ClickMyReviews) })
                MenuItem(title = "나의 취향 분석", onClick = { onIntent(MyPageIntent.ClickTasteAnalysis) })
                MenuItem(title = "설정", onClick = { onIntent(MyPageIntent.ClickSettings) })
                MenuItem(
                    title = "로그아웃",
                    titleColor = AppColor.SubRed,
                    trailing = null,
                    onClick = { onIntent(MyPageIntent.ClickLogout) },
                )
            }

            Spacer(modifier = Modifier.height(BOTTOM_SCROLL_SPACER))
        }
    }
}

@Composable
private fun ProfileRow(
    name: String,
    profileUrl: String?,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileImage(
            url = profileUrl,
            fallbackResId = R.drawable.ic_profile_80,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = name,
            style = AppTextStyle.Headline,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.width(4.dp))
        AppIconButton(
            iconResId = R.drawable.ic_more_24,
            onIconClick = onMoreClick,
        )
    }
}

@Composable
private fun ProfileImage(
    url: String?,
    @DrawableRes fallbackResId: Int,
    modifier: Modifier = Modifier,
) {
    val avatarModifier = modifier.size(80.dp).clip(CircleShape)

    @Composable
    fun Fallback(desc: String) {
        Image(
            painter = painterResource(fallbackResId),
            contentDescription = desc,
            modifier = avatarModifier,
            contentScale = ContentScale.Crop,
        )
    }

    if (url.isNullOrBlank()) {
        Fallback("프로필 이미지(기본)")
        return
    }

    SubcomposeAsyncImage(
        model = url,
        contentDescription = "프로필 이미지",
        modifier = avatarModifier,
        contentScale = ContentScale.Crop,
        loading = { Fallback("프로필 이미지(로딩)") },
        error = { Fallback("프로필 이미지(에러)") },
    )
}

@Composable
private fun MenuItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    titleColor: Color = AppColor.TextPrimary,
    titleStyle: TextStyle = AppTextStyle.Title02Light,
    trailing: (@Composable () -> Unit)? = {
        Icon(
            painter = painterResource(R.drawable.ic_more_24),
            tint = Color.Unspecified,
            contentDescription = null,
        )
    },
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier)
                .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = titleStyle,
            color = titleColor,
        )
        trailing?.invoke()
    }
}
