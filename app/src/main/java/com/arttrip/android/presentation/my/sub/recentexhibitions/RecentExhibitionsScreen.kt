package com.arttrip.android.presentation.my.sub.recentexhibitions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.empty.AppEmptyState
import com.arttrip.android.core.ui.component.skeleton.StaticSkeleton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.presentation.my.sub.recentexhibitions.contract.RecentExhibitionsIntent
import com.arttrip.android.presentation.my.sub.recentexhibitions.contract.RecentExhibitionsState

private val CONTENT_HORIZONTAL_PADDING = 24.dp
private val TOP_SCROLL_SPACER = 16.dp
private val BOTTOM_SCROLL_SPACER = 48.dp
private val EXHIBITION_ITEM_GAP = 16.dp

@Composable
fun RecentExhibitionsScreen(
    innerPadding: PaddingValues,
    state: RecentExhibitionsState,
    onIntent: (RecentExhibitionsIntent) -> Unit,
) {
    val listState = rememberLazyListState()

    Column(modifier = Modifier.padding(innerPadding)) {
        AppTopBar(
            title = "최근 본 전시",
            leading = {
                AppIconButton(
                    iconResId = R.drawable.ic_back_24,
                    contentDescription = "뒤로가기",
                    onIconClick = {
                        onIntent(RecentExhibitionsIntent.BackClicked)
                    },
                )
            },
        )

        if (state.isEmpty) {
            AppEmptyState(
                modifier = Modifier.fillMaxWidth(),
                iconResId = R.drawable.ic_empty_exhitbition_96,
                message = "최근 본 전시가 없습니다.",
            )
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = CONTENT_HORIZONTAL_PADDING),
                state = listState,
                contentPadding = PaddingValues(top = TOP_SCROLL_SPACER, bottom = BOTTOM_SCROLL_SPACER),
                verticalArrangement = Arrangement.spacedBy(EXHIBITION_ITEM_GAP),
            ) {
                items(state.exhibitions) { exhibition ->
                    ExhibitionItem(
                        title = exhibition.title,
                        hallName = exhibition.hallName,
                        url = exhibition.url,
                    )
                }
            }
        }
    }
}

@Composable
private fun ExhibitionItem(
    title: String,
    hallName: String,
    url: String?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExhibitionThumb(url = url)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                title,
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                hallName,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextTertiary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun ExhibitionThumb(
    modifier: Modifier = Modifier,
    url: String?,
) {
    val shape = RoundedCornerShape(8.dp)
    val size = 100.dp

    if (url.isNullOrEmpty()) {
        StaticSkeleton(
            modifier = modifier.size(size),
            shape = shape,
        )

        return
    }
    SubcomposeAsyncImage(
        modifier =
            modifier
                .size(size)
                .clip(shape),
        model = url,
        contentDescription = "Exhibition thumbnail",
        contentScale = ContentScale.Crop,
        loading = { StaticSkeleton(modifier = Modifier.matchParentSize()) },
        error = { StaticSkeleton(modifier = Modifier.matchParentSize()) },
    )
}
