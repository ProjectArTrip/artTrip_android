package com.arttrip.app.presentation.home.sub.genre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.arttrip.app.R
import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.exhibition.SortType
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.core.ui.component.appbar.AppTopBar
import com.arttrip.app.core.ui.component.button.AppButton
import com.arttrip.app.core.ui.component.button.AppFilterChip
import com.arttrip.app.core.ui.component.button.AppFilterChipCase
import com.arttrip.app.core.ui.component.button.AppIconButton
import com.arttrip.app.core.ui.component.list.ExhibitionListItem
import com.arttrip.app.core.ui.component.sheet.AppBottomSheetTopBar
import com.arttrip.app.core.ui.component.sheet.AppModalBottomSheet
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.presentation.home.sub.genre.contract.GenreIntent
import com.arttrip.app.presentation.home.sub.genre.contract.GenreState
import com.arttrip.app.presentation.home.ui.feedback.ErrorExhibitionList
import com.arttrip.app.presentation.home.ui.feedback.LoadingExhibitionList
import com.arttrip.app.presentation.home.ui.feedback.NoExhibitionList

@Composable
fun GenreScreen(
    innerPadding: PaddingValues,
    state: GenreState,
    hasUnread: Boolean = false,
    onIntent: (GenreIntent) -> Unit,
    country: ForeignCountry?,
    genre: ExhibitionGenre,
    exhibitionList: LazyPagingItems<Exhibition>,
    bookmarked: Map<Int, Boolean>,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Spacer(
                modifier =
                    Modifier
                        .height(16.dp),
            )
            AppTopBar(
                title = "${state.selectedGenre?.label} 전시 추천",
                leading = {
                    AppIconButton(
                        iconResId = R.drawable.ic_back_24,
                        contentDescription = "Back Button",
                        onIconClick = {
                            onIntent(GenreIntent.BackClicked)
                        },
                    )
                },
                actions = {
                    AppIconButton(
                        iconResId = if (hasUnread) R.drawable.ic_alert_badge_24 else R.drawable.ic_alert_24,
                        contentDescription = "Notification Button",
                        onIconClick = {
                            onIntent(GenreIntent.NotificationIconClicked)
                        },
                    )
                },
            )
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "총 ${state.exhibitTotalCount}개",
                    style = AppTextStyle.Title02Bold,
                    color = AppColor.TextPrimary,
                )
                AppIconButton(
                    iconResId = R.drawable.ic_filter_24,
                    contentDescription = "Filter Button",
                    onIconClick = {
                        onIntent(GenreIntent.OpenFilterSheet)
                    },
                )
            }
            ExhibitionList(
                exhibitionList = exhibitionList,
                bookmarked = bookmarked,
                onExhibitionClick = { id -> onIntent(GenreIntent.ExhibitionClicked(id)) },
                onLikeClick = { id -> onIntent(GenreIntent.LikeClicked(id)) },
            )
        }
    }
    GenreFilterBottomSheet(
        visible = state.isFilterSheetVisible,
        onIntent = onIntent,
        state = state,
    )
}

@Composable
fun GenreFilterBottomSheet(
    visible: Boolean,
    onIntent: (GenreIntent) -> Unit,
    state: GenreState,
) {
    var selectedSortType by remember(state.selectedSortType) { mutableStateOf(state.selectedSortType) }
    var selectedGenre by remember(state.selectedGenre) { mutableStateOf(state.selectedGenre) }

    AppModalBottomSheet(
        visible = visible,
        topBar = AppBottomSheetTopBar.Header(),
        onDismissRequest = {
            onIntent(GenreIntent.CloseFilterSheet)
        },
        content = {
            Spacer(
                modifier =
                    Modifier
                        .height(16.dp),
            )
            Text(
                "정렬",
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPrimary,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(8.dp),
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                SortType.entries.forEach { sortType ->
                    AppFilterChip(
                        case = AppFilterChipCase.Case02,
                        text = sortType.label,
                        selected = selectedSortType == sortType,
                        onClick = {
                            selectedSortType = sortType
                        },
                    )
                }
            }
            Spacer(
                modifier =
                    Modifier
                        .height(15.dp),
            )
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = AppColor.Gray100),
            )
            Spacer(
                modifier =
                    Modifier
                        .height(8.dp),
            )
            Text(
                "전시 장르",
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPrimary,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(8.dp),
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ExhibitionGenre.entries.forEach { genre ->
                    AppFilterChip(
                        case = AppFilterChipCase.Case02,
                        text = genre.label,
                        selected = selectedGenre == genre,
                        onClick = {
                            selectedGenre = genre
                        },
                    )
                }
            }
            Spacer(
                modifier =
                    Modifier
                        .height(48.dp),
            )
            AppButton(
                text = "적용하기",
                onClick = {
                    onIntent(GenreIntent.CloseFilterSheet)
                    onIntent(GenreIntent.SelectSortType(selectedSortType))
                    onIntent(GenreIntent.SelectGenre(selectedGenre!!))
                },
            )
            Spacer(
                modifier =
                    Modifier
                        .height(16.dp),
            )
        },
    )
}

@Composable
fun ExhibitionList(
    exhibitionList: LazyPagingItems<Exhibition>,
    bookmarked: Map<Int, Boolean>,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(exhibitionList.loadState.refresh) {
        if (exhibitionList.loadState.refresh is LoadState.Loading) {
            listState.scrollToItem(0)
        }
    }

    when {
        exhibitionList.loadState.refresh is LoadState.Loading -> {
            LoadingExhibitionList()
        }
        exhibitionList.loadState.refresh is LoadState.Error -> {
            ErrorExhibitionList()
        }
        exhibitionList.itemCount == 0 -> {
            NoExhibitionList()
        }
        else -> {
            LazyColumn(
                state = listState,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                items(exhibitionList.itemCount) { index ->
                    exhibitionList[index]?.let { exhibition ->
                        ExhibitionListItem(
                            posterUrl = exhibition.posterUrl,
                            location = null,
                            title = exhibition.title,
                            hallName = exhibition.hallName,
                            period = exhibition.period,
                            status = exhibition.status,
                            isLiked = bookmarked[exhibition.id] ?: exhibition.isBookmarked,
                            onItemClick = { onExhibitionClick(exhibition.id) },
                            onLikeClick = { onLikeClick(exhibition.id) },
                        )
                    }
                }
                item {
                    Spacer(
                        modifier =
                            Modifier
                                .height(12.dp),
                    )
                }
            }
        }
    }
}
