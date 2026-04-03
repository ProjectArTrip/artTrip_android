package com.arttrip.android.presentation.home.sub.datefilterresult

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.model.enums.exhibition.ExhibitionConstants
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.list.ExhibitionListItem
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.rememberScrollUpVisible
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultState

@Composable
fun DateFilterResultScreen(
    innerPadding: PaddingValues,
    state: DateFilterResultState,
) {
    val listState = rememberLazyListState()
    val countVisible = rememberScrollUpVisible(listState).value
    Column(modifier = Modifier.padding(innerPadding)) {
        AppTopBar(
            leading = null,
            actions = {
                AppIconButton(
                    iconResId = R.drawable.ic_alert_24,
                    onIconClick = {},
                )
                AppIconButton(
                    modifier = Modifier,
                    iconResId = R.drawable.ic_calendar_24,
                    contentDescription = "달력",
                ) {
                }
                AppIconButton(
                    modifier = Modifier,
                    iconResId = R.drawable.ic_search_24,
                    contentDescription = "검색",
                ) {
                }
            },
        )
        DateFilterResultTopBar(
            visible = countVisible,
            location = state.location,
            date = state.dateStr,
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 24.dp),
            state = listState,
            contentPadding = PaddingValues(top = 12.dp, bottom = 56.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                count = state.list.size,
                key = { index -> state.list[index].id },
            ) { index ->
                val item = state.list[index]
                ExhibitionListItem(
                    posterUrl = item.posterUrl,
                    location = if (item.country == ExhibitionConstants.DOMESTIC_COUNTRY) item.region else item.country,
                    title = item.title,
                    hallName = item.hallName,
                    period = item.period,
                    status = item.status,
                    isLiked = item.isBookmarked,
                    onLikeClick = {},
                    onItemClick = {},
                )
            }
        }
    }
}

@Composable
private fun DateFilterResultTopBar(
    visible: Boolean,
    location: String,
    date: String,
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                location,
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                date,
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextSecondary,
            )
        }
    }
}
