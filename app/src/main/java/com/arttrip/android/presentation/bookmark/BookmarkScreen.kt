package com.arttrip.android.presentation.bookmark

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.arttrip.android.R
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionStatus
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppButton
import com.arttrip.android.core.ui.component.button.AppButtonVariant
import com.arttrip.android.core.ui.component.button.AppFilterChip
import com.arttrip.android.core.ui.component.button.AppFilterChipCase
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.button.LikeButton
import com.arttrip.android.core.ui.component.empty.AppEmptyState
import com.arttrip.android.core.ui.component.sheet.AppBottomSheetTopBar
import com.arttrip.android.core.ui.component.sheet.AppModalBottomSheet
import com.arttrip.android.core.ui.component.tag.AppTag
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.core.util.rememberScrollUpVisible
import com.arttrip.android.domain.model.favorite.Bookmark
import com.arttrip.android.presentation.bookmark.contract.BookmarkIntent
import com.arttrip.android.presentation.bookmark.contract.BookmarkSort
import com.arttrip.android.presentation.bookmark.contract.BookmarkState
import kotlinx.coroutines.flow.Flow

@Composable
fun BookmarkScreen(
    innerPadding: PaddingValues,
    state: BookmarkState,
    bookmarks: LazyPagingItems<Bookmark>,
    onIntent: (BookmarkIntent) -> Unit,
    onSetBookmarkFromRemote: (exhibitId: Int, isBookmarked: Boolean) -> Unit,
    bookmarkedFlow: (exhibitId: Int) -> Flow<Boolean>,
) {
    val listState = rememberLazyListState()
    val countVisible = rememberScrollUpVisible(listState).value

    LaunchedEffect(state.sort) {
        listState.scrollToItem(0)
    }

    LaunchedEffect(bookmarks.loadState.refresh) {
        if (bookmarks.loadState.refresh is LoadState.NotLoading) {
            listState.animateScrollToItem(0)
        }
    }

    Column(modifier = Modifier.padding(innerPadding)) {
        AppTopBar(
            title = "즐겨찾기",
            leading = null,
            actions = {
                AppIconButton(
                    iconResId = R.drawable.ic_alert_24,
                    onIconClick = {},
                )
            },
        )
        BookmarkListTopBar(
            visible = countVisible,
            count = state.bookmarkTotalCount ?: 0,
            sort = state.sort,
            onSortChange = { sort -> onIntent(BookmarkIntent.ChangeSort(sort)) },
            onFilterClick = { onIntent(BookmarkIntent.FilterSheetOpened) },
        )

        if (state.isEmpty) {
            AppEmptyState(
                modifier = Modifier.fillMaxWidth(),
                iconResId = R.drawable.ic_empty_bookmark_96,
                message = "즐겨찾기 항목이 없습니다.",
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 24.dp),
                state = listState,
                contentPadding = PaddingValues(top = 8.dp, bottom = 48.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    count = bookmarks.itemCount,
                    key = bookmarks.itemKey { it.exhibitId },
                ) { index ->
                    val item = bookmarks[index] ?: return@items
                    LaunchedEffect(item.exhibitId) {
                        onSetBookmarkFromRemote(item.exhibitId, true)
                    }
                    val isLiked by bookmarkedFlow(item.exhibitId).collectAsStateWithLifecycle(true)
                    BookmarkItem(
                        bookmark = item,
                        isLiked = isLiked,
                        onItemClick = { onIntent(BookmarkIntent.ClickItem(item.exhibitId)) },
                        onLikeClick = { onIntent(BookmarkIntent.ToggleBookmark(item.exhibitId)) },
                    )
                }
            }
        }

        AppModalBottomSheet(
            visible = state.isFilterSheetVisible,
            onDismissRequest = { onIntent(BookmarkIntent.FilterSheetDismissed) },
            topBar = AppBottomSheetTopBar.Header(""),
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            LocationChipSection(
                title = "해외",
                items = ForeignCountry.entries,
                selected = state.editingLocationFilter.foreignCountries,
                labelOf = { it.label },
                onToggle = { country -> onIntent(BookmarkIntent.ToggleForeignCountry(country)) },
            )
            Spacer(modifier = Modifier.height(19.dp))
            HorizontalDivider(thickness = 1.dp, color = AppColor.Gray100)
            Spacer(modifier = Modifier.height(16.dp))

            LocationChipSection(
                title = "국내",
                items = DomesticRegion.entries,
                selected = state.editingLocationFilter.domesticRegions,
                labelOf = { it.label },
                onToggle = { region -> onIntent(BookmarkIntent.ToggleDomesticRegion(region)) },
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier =
                        Modifier.noRippleClickable {
                            onIntent(BookmarkIntent.ResetFilter)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_reset_24),
                        contentDescription = "Reset",
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "전체 해제",
                        style = AppTextStyle.Body02Light,
                        color = AppColor.TextSecondary,
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                AppButton(
                    modifier = Modifier.weight(1f),
                    variant = AppButtonVariant.Primary,
                    onClick = { onIntent(BookmarkIntent.ClickSearch) },
                    enabled = state.isSearchEnabled,
                    text = "찾아보기",
                )
            }
        }
    }
}

@Composable
private fun BookmarkListTopBar(
    visible: Boolean,
    count: Int,
    sort: BookmarkSort,
    onSortChange: (BookmarkSort) -> Unit,
    onFilterClick: () -> Unit,
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
                "총 ${count}개",
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
            )

            Spacer(modifier = Modifier.weight(1f))

            SortTextButton(
                text = "최신순",
                selected = sort == BookmarkSort.LATEST,
            ) {
                if (sort != BookmarkSort.LATEST) onSortChange(BookmarkSort.LATEST)
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
                selected = sort == BookmarkSort.DEADLINE,
            ) {
                if (sort != BookmarkSort.DEADLINE) onSortChange(BookmarkSort.DEADLINE)
            }

            Spacer(modifier = Modifier.width(12.dp))

            AppIconButton(
                iconResId = R.drawable.ic_filter_24,
                onIconClick = onFilterClick,
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
        color = if (selected) AppColor.TextPoint else AppColor.TextPrimary,
        modifier = Modifier.noRippleClickable(onClick = onClick),
    )
}

@Composable
fun BookmarkItem(
    bookmark: Bookmark,
    isLiked: Boolean,
    onItemClick: () -> Unit,
    onLikeClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .noRippleClickable { onItemClick() }
                .padding(end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExhibitionThumbWithActions(
            url = bookmark.posterUrl,
            isLiked = isLiked,
            status = bookmark.status,
            onLikeClick = onLikeClick,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            bookmark.country?.let {
                Text(
                    text = it,
                    style = AppTextStyle.Body01Regular,
                    color = AppColor.TextPoint,
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
            Text(
                text = bookmark.title,
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = bookmark.hallName,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextTertiary,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = bookmark.period,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextTertiary,
            )
        }
    }
}

@Composable
fun ExhibitionThumbWithActions(
    url: String,
    isLiked: Boolean,
    status: ExhibitionStatus,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(100.dp),
    ) {
        AsyncImage(
            modifier =
                Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(8.dp)),
            model = url,
            contentDescription = "Exhibition thumbnail",
            contentScale = ContentScale.Crop,
        )

        LikeButton(
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp),
            isSelected = isLiked,
            onClick = onLikeClick,
        )

        AppTag(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 0.dp, end = 0.dp),
            status = status,
        )
    }
}

@Composable
fun <T> LocationChipSection(
    title: String,
    items: List<T>,
    selected: Set<T>,
    labelOf: (T) -> String,
    onToggle: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = title, style = AppTextStyle.Body01Bold, color = AppColor.TextPrimary)
        Spacer(Modifier.height(18.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items.forEach { item ->
                AppFilterChip(
                    text = labelOf(item),
                    case = AppFilterChipCase.Case02,
                    selected = item in selected,
                    onClick = { onToggle(item) },
                )
            }
        }
    }
}
