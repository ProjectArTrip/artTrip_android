package com.arttrip.android.presentation.my.sub.settings.sub.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.presentation.my.sub.settings.sub.notice.contract.NoticeIntent
import com.arttrip.android.presentation.my.sub.settings.sub.notice.contract.NoticeState

private val CONTENT_HORIZONTAL_PADDING = 24.dp
private val BOTTOM_SCROLL_SPACER = 48.dp

@Composable
fun NoticeScreen(
    innerPadding: PaddingValues,
    state: NoticeState,
    onIntent: (NoticeIntent) -> Unit,
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
            title = "공지사항",
            leading = {
                AppIconButton(
                    iconResId = R.drawable.ic_back_24,
                    contentDescription = "뒤로가기",
                    onIconClick = {
                        onIntent(NoticeIntent.BackClicked)
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
            Spacer(modifier = Modifier.height(BOTTOM_SCROLL_SPACER))
        }
    }
}
