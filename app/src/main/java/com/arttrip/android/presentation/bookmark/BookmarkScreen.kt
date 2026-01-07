package com.arttrip.android.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.presentation.bookmark.contract.BookmarkIntent
import com.arttrip.android.presentation.bookmark.contract.BookmarkSort
import com.arttrip.android.presentation.bookmark.contract.BookmarkState

@Composable
fun BookmarkScreen(
    innerPadding: PaddingValues,
    state: BookmarkState,
    onIntent: (BookmarkIntent) -> Unit,
) {
    Column(modifier = Modifier.padding(innerPadding)) {
        AppTopBar(
            title = "즐겨찾기",
            actions = {
                AppIconButton(
                    iconResId = R.drawable.ic_alert_24,
                    onIconClick = {},
                )
            },
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "총 24개",
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
            )

            Spacer(modifier = Modifier.weight(1f))

            SortTextButton(
                text = "최신순",
                selected = state.sort == BookmarkSort.LATEST,
            ) {
                if (state.sort != BookmarkSort.LATEST) {
                    onIntent(BookmarkIntent.ChangeSort(BookmarkSort.LATEST))
                }
            }

            VerticalDivider(
                modifier =
                    Modifier
                        .padding(horizontal = 12.dp)
                        .height(12.dp),
                thickness = 1.dp,
                color = AppColor.Gray100,
            )

            SortTextButton(
                text = "마감순",
                selected = state.sort == BookmarkSort.DEADLINE,
            ) {
                if (state.sort != BookmarkSort.DEADLINE) {
                    onIntent(BookmarkIntent.ChangeSort(BookmarkSort.DEADLINE))
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            AppIconButton(
                iconResId = R.drawable.ic_filter_24,
                onIconClick = {},
            )
        }
    }
}

@Composable
private fun SortTextButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        style = if (selected) AppTextStyle.Body01Bold else AppTextStyle.Body01Regular,
        color = if (selected) AppColor.TextPrimary else AppColor.TextPoint,
        modifier = Modifier.noRippleClickable(onClick = onClick),
    )
}
