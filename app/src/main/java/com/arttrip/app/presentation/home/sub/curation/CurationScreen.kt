package com.arttrip.app.presentation.home.sub.curation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.arttrip.app.R
import com.arttrip.app.core.ui.component.appbar.AppTopBar
import com.arttrip.app.core.ui.component.button.AppIconButton
import com.arttrip.app.core.ui.component.list.ExhibitionListItem
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.presentation.home.sub.curation.contract.CurationIntent
import com.arttrip.app.presentation.home.sub.curation.contract.CurationState
import com.arttrip.app.presentation.home.ui.feedback.ErrorExhibitionList
import com.arttrip.app.presentation.home.ui.feedback.LoadingExhibitionList
import com.arttrip.app.presentation.home.ui.feedback.NoExhibitionList

@Composable
fun CurationScreen(
    innerPadding: PaddingValues,
    state: CurationState,
    hasUnread: Boolean = false,
    onIntent: (CurationIntent) -> Unit,
    exhibitionList: LazyPagingItems<Exhibition>,
    bookmarked: Map<Int, Boolean>,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(exhibitionList.loadState.refresh) {
        if (exhibitionList.loadState.refresh is LoadState.Loading) {
            listState.scrollToItem(0)
        }
    }

    LazyColumn(
        state = listState,
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AppTopBar(
                title = state.title,
                leading = {
                    AppIconButton(
                        iconResId = R.drawable.ic_back_24,
                        contentDescription = "Back Button",
                        onIconClick = { onIntent(CurationIntent.BackClicked) },
                    )
                },
                actions = {
                    AppIconButton(
                        iconResId = if (hasUnread) R.drawable.ic_alert_badge_24 else R.drawable.ic_alert_24,
                        contentDescription = "Notification Button",
                        onIconClick = { onIntent(CurationIntent.NotificationIconClicked) },
                    )
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        when {
            exhibitionList.loadState.refresh is LoadState.Loading -> {
                item { LoadingExhibitionList() }
            }
            exhibitionList.loadState.refresh is LoadState.Error -> {
                item { ErrorExhibitionList() }
            }
            exhibitionList.itemCount == 0 -> {
                item { NoExhibitionList() }
            }
            else -> {
                items(exhibitionList.itemCount) { index ->
                    exhibitionList[index]?.let { exhibition ->
                        ExhibitionListItem(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            posterUrl = exhibition.posterUrl,
                            location = null,
                            title = exhibition.title,
                            hallName = exhibition.hallName,
                            period = exhibition.period,
                            status = exhibition.status,
                            isLiked = bookmarked[exhibition.id] ?: exhibition.isBookmarked,
                            onItemClick = { onIntent(CurationIntent.ExhibitionClicked(exhibition.id)) },
                            onLikeClick = { onIntent(CurationIntent.LikeClicked(exhibition.id)) },
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
