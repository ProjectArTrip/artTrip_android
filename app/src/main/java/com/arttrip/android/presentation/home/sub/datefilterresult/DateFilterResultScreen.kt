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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.arttrip.android.R
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.list.ExhibitionListItem
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.rememberScrollUpVisible
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultIntent
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultState
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.ExhibitionLocation
import com.arttrip.android.presentation.home.ui.datefilter.DateFilterBottomSheet
import com.arttrip.android.presentation.home.ui.datefilter.FilterChips
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DateFilterResultScreen(
    innerPadding: PaddingValues,
    state: DateFilterResultState,
    onIntent: (DateFilterResultIntent) -> Unit,
    exhibitionItems: LazyPagingItems<Exhibition>,
) {
    val listState = rememberLazyListState()
    val countVisible = rememberScrollUpVisible(listState).value
    val formatter = remember { DateTimeFormatter.ofPattern("MM.dd (E)", Locale.KOREAN) }

    LaunchedEffect(exhibitionItems.loadState.refresh) {
        if (exhibitionItems.loadState.refresh is LoadState.Loading) {
            listState.scrollToItem(0)
        }
    }

    Column(modifier = Modifier.padding(innerPadding)) {
        AppTopBar(
            leading = {
                AppIconButton(
                    iconResId = R.drawable.ic_back_24,
                    contentDescription = "뒤로가기",
                    onIconClick = { onIntent(DateFilterResultIntent.BackClicked) },
                )
            },
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
                    onIntent(DateFilterResultIntent.DateFilterIconClicked)
                }
            },
        )
        DateFilterResultTopBar(
            visible = countVisible,
            location = state.location?.label ?: "",
            date =
                if (state.startDate != null && state.endDate != null) {
                    "${state.startDate.format(formatter)} - ${state.endDate.format(formatter)}"
                } else {
                    ""
                },
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 24.dp),
            state = listState,
            contentPadding = PaddingValues(top = 12.dp, bottom = 56.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                count = exhibitionItems.itemCount,
                key = { index -> exhibitionItems[index]?.id ?: index },
            ) { index ->
                val item = exhibitionItems[index] ?: return@items
                ExhibitionListItem(
                    posterUrl = item.posterUrl,
                    location = null,
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

        DateFilterBottomSheet(
            visible = state.isDateFilterSheetVisible,
            title =
                when (state.location) {
                    is ExhibitionLocation.Domestic -> "지역 및 날짜 선택"
                    is ExhibitionLocation.Foreign -> "국가 및 날짜 선택"
                    null -> ""
                },
            startDate = state.dateFilterStartDate,
            endDate = state.dateFilterEndDate,
            isApplyEnabled = state.dateFilterSelectedLocation != null && state.dateFilterEndDate != null,
            locationTitle =
                when (state.location) {
                    is ExhibitionLocation.Domestic -> "지역"
                    is ExhibitionLocation.Foreign -> "국가"
                    null -> ""
                },
            locationDescription = state.dateFilterSelectedLocation?.label,
            onDayClick = { date -> onIntent(DateFilterResultIntent.DateFilterDayClicked(date)) },
            onDateSectionOpen = { onIntent(DateFilterResultIntent.DateFilterDateSectionOpened) },
            onResetClick = { onIntent(DateFilterResultIntent.DateFilterResetClicked) },
            onApplyClick = { onIntent(DateFilterResultIntent.DateFilterApplyClicked) },
            onDismissRequest = { onIntent(DateFilterResultIntent.DateFilterSheetDismissed) },
            locationChips = {
                when (state.location) {
                    is ExhibitionLocation.Domestic ->
                        FilterChips(
                            items = DomesticRegion.entries,
                            selected = (state.dateFilterSelectedLocation as? ExhibitionLocation.Domestic)?.region,
                            labelOf = { it.label },
                            onItemClick = { onIntent(DateFilterResultIntent.DateFilterLocationSelected(ExhibitionLocation.Domestic(it))) },
                        )
                    is ExhibitionLocation.Foreign ->
                        FilterChips(
                            items = ForeignCountry.entries,
                            selected = (state.dateFilterSelectedLocation as? ExhibitionLocation.Foreign)?.country,
                            labelOf = { it.label },
                            onItemClick = { onIntent(DateFilterResultIntent.DateFilterLocationSelected(ExhibitionLocation.Foreign(it))) },
                        )
                    null -> {}
                }
            },
        )
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
