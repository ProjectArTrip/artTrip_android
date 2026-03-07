package com.arttrip.android.presentation.home.sub.schedule

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppFilterChip
import com.arttrip.android.core.ui.component.button.AppFilterChipCase
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.sheet.AppBottomSheetTopBar
import com.arttrip.android.core.ui.component.sheet.AppModalBottomSheet
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.presentation.home.sub.schedule.contract.ScheduleIntent
import com.arttrip.android.presentation.home.sub.schedule.contract.ScheduleState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ScheduleScreen(
    innerPadding: PaddingValues,
    state: ScheduleState,
    onIntent: (ScheduleIntent) -> Unit,
    date: LocalDate,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            AppTopBar(
                title = date.format(DateTimeFormatter.ofPattern("M월 d일")) + " 전시 일정",
                leading = {
                    AppIconButton(
                        iconResId = R.drawable.ic_back_24,
                        contentDescription = "Back Button",
                        onIconClick = { onIntent(ScheduleIntent.BackClicked) },
                    )
                },
                actions = {
                    AppIconButton(
                        iconResId = R.drawable.ic_alert_badge_24,
                        contentDescription = "Notification Button",
                        onIconClick = { onIntent(ScheduleIntent.NotificationIconClicked) },
                    )
                },
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "총 N개",
                    style = AppTextStyle.Title02Bold,
                    color = AppColor.TextPrimary,
                )
                AppIconButton(
                    iconResId = R.drawable.ic_filter_24,
                    contentDescription = "Filter Button",
                    onIconClick = { onIntent(ScheduleIntent.OpenFilterSheet) },
                )
            }
        }
    }

    ScheduleFilterBottomSheet(
        visible = state.isFilterSheetVisible,
        onIntent = onIntent,
        state = state,
    )
}

@Composable
fun ScheduleFilterBottomSheet(
    visible: Boolean,
    onIntent: (ScheduleIntent) -> Unit,
    state: ScheduleState,
) {
    AppModalBottomSheet(
        visible = visible,
        topBar = AppBottomSheetTopBar.Header(),
        onDismissRequest = { onIntent(ScheduleIntent.CloseFilterSheet) },
        content = {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "정렬",
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPrimary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SortType.entries.forEach { sortType ->
                    AppFilterChip(
                        case = AppFilterChipCase.Case02,
                        text = sortType.label,
                        selected = state.selectedSortType == sortType,
                        onClick = { onIntent(ScheduleIntent.SelectSortType(sortType)) },
                    )
                }
            }
            Spacer(modifier = Modifier.height(36.dp))
        },
    )
}
