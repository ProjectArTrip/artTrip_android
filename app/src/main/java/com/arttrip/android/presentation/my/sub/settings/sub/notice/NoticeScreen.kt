package com.arttrip.android.presentation.my.sub.settings.sub.notice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.empty.AppEmptyState
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
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
    val listState = rememberLazyListState()
    var expandedIds by rememberSaveable { mutableStateOf(setOf<Int>()) }
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

        if (state.isEmpty) {
            AppEmptyState(
                modifier = Modifier.fillMaxWidth(),
                iconResId = R.drawable.ic_empty_notice_96,
                message = "공지사항이 존재하지 않습니다.",
            )
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                state = listState,
                contentPadding = PaddingValues(top = 16.dp, bottom = BOTTOM_SCROLL_SPACER),
            ) {
                items(state.notices, key = { it.id }) { notice ->
                    val expanded = expandedIds.contains(notice.id)
                    NoticeMenuItem(
                        title = notice.title,
                        date = notice.date,
                        content = notice.content,
                        expanded = expanded,
                        onMenuClick = {
                            expandedIds =
                                if (expanded) {
                                    expandedIds - notice.id
                                } else {
                                    expandedIds + notice.id
                                }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun NoticeMenuItem(
    title: String,
    date: String,
    content: String,
    expanded: Boolean,
    onMenuClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        NoticeHeader(title, date, onMenuClick)
        NoticeExpandedContent(expanded, content)
    }
}

@Composable
private fun NoticeHeader(
    title: String,
    date: String,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(top = 20.dp),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = CONTENT_HORIZONTAL_PADDING),
            text = title,
            style = AppTextStyle.Title02Light,
            color = AppColor.TextPrimary,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(horizontal = CONTENT_HORIZONTAL_PADDING),
            text = date,
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextTertiary,
        )

        Spacer(Modifier.height(19.dp))
        HorizontalDivider(color = AppColor.Gray100, thickness = 1.dp)
    }
}

@Composable
private fun NoticeExpandedContent(
    expanded: Boolean,
    content: String,
) {
    AnimatedVisibility(
        visible = expanded,
        enter = expandVertically(animationSpec = tween(220)) + fadeIn(animationSpec = tween(220)),
        exit = shrinkVertically(animationSpec = tween(180)) + fadeOut(animationSpec = tween(180)),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(AppColor.SubLightGray),
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp, bottom = 19.dp, start = 24.dp, end = 24.dp),
                text = content,
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPrimary,
            )
            HorizontalDivider(color = AppColor.Gray100, thickness = 1.dp)
        }
    }
}
