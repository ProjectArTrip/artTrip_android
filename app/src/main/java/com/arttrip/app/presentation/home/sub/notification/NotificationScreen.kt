package com.arttrip.app.presentation.home.sub.notification

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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.arttrip.app.R
import com.arttrip.app.core.ui.component.appbar.AppTopBar
import com.arttrip.app.core.ui.component.button.AppIconButton
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.domain.model.notification.Notification
import com.arttrip.app.domain.model.notification.toRelativeDateText
import com.arttrip.app.presentation.home.sub.notification.contract.NotificationIntent

@Composable
fun NotificationScreen(
    innerPadding: PaddingValues,
    onIntent: (NotificationIntent) -> Unit,
    notificationItems: LazyPagingItems<Notification>,
    localReadIds: Set<Int> = emptySet(),
) {
    val listState = rememberLazyListState()

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(16.dp))
            AppTopBar(
                title = "알림",
                leading = {
                    AppIconButton(
                        iconResId = R.drawable.ic_back_24,
                        contentDescription = "Back Button",
                        onIconClick = { onIntent(NotificationIntent.BackClicked) },
                    )
                },
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(bottom = 28.dp),
            ) {
                items(notificationItems.itemCount) { idx ->
                    val item = notificationItems[idx] ?: return@items
                    NotificationItem(
                        isRead = item.isRead || item.userNoticeId in localReadIds,
                        title = item.title,
                        message = item.body,
                        relativeTime = item.createdAt.toRelativeDateText(),
                        onClick = {
                                onIntent(
                                    NotificationIntent.NotificationItemClicked(
                                        userNoticeId = item.userNoticeId,
                                        action = item.action,
                                        referenceId = item.referenceId,
                                    ),
                                )
                            },
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationItem(
    isRead: Boolean,
    title: String,
    message: String,
    relativeTime: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                painter = painterResource(if (isRead) R.drawable.ic_alert_24 else R.drawable.ic_alert_badge_24),
                contentDescription = "Alert Icon",
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Spacer(modifier = Modifier.height(1.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = title,
                        style = AppTextStyle.Title02Bold,
                        color = AppColor.TextPrimary,
                    )
                    Text(
                        text = relativeTime,
                        style = AppTextStyle.Body02Light,
                        color = AppColor.TextTertiary,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                ) {
                    Text(
                        modifier = Modifier.height(40.dp),
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
