package com.arttrip.android.presentation.my.sub.myreviews

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.button.ReviewButton
import com.arttrip.android.core.ui.component.dialog.AppDialog
import com.arttrip.android.core.ui.component.empty.AppEmptyState
import com.arttrip.android.core.ui.component.skeleton.StaticSkeleton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.rememberScrollUpVisible
import com.arttrip.android.domain.model.review.UserReview
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsIntent
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsState

private val CONTENT_HORIZONTAL_PADDING = 24.dp
private val REVIEW_ITEM_GAP = 24.dp
private val BOTTOM_SCROLL_SPACER = 48.dp

@Composable
fun MyReviewsScreen(
    innerPadding: PaddingValues,
    state: MyReviewsState,
    onIntent: (MyReviewsIntent) -> Unit,
    reviewItems: LazyPagingItems<UserReview>,
) {
    val listState = rememberLazyListState()
    val countVisible = rememberScrollUpVisible(listState).value

    android.util.Log.d("MyReviewsScreen", "itemCount=${reviewItems.itemCount}")
    val totalCount = state.reviewTotalCount

    Column(modifier = Modifier.padding(innerPadding)) {
        AppTopBar(
            title = "나의 리뷰",
            leading = {
                AppIconButton(
                    iconResId = R.drawable.ic_back_24,
                    contentDescription = "뒤로가기",
                    onIconClick = {
                        onIntent(MyReviewsIntent.BackClicked)
                    },
                )
            },
        )

        when (totalCount) {
            null -> {
            }
            0 -> {
                AppEmptyState(
                    modifier = Modifier.fillMaxWidth(),
                    iconResId = R.drawable.ic_empty_review_96,
                    message = "작성된 리뷰가 없습니다.",
                )
            }
            else -> {
                ReviewListTopBar(
                    visible = countVisible,
                    count = totalCount,
                )

                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = CONTENT_HORIZONTAL_PADDING),
                    state = listState,
                    contentPadding = PaddingValues(top = 8.dp, bottom = BOTTOM_SCROLL_SPACER),
                    verticalArrangement = Arrangement.spacedBy(REVIEW_ITEM_GAP),
                ) {
                    items(reviewItems.itemCount) { idx ->
                        val item = reviewItems[idx] ?: return@items
                        ReviewItem(
                            title = item.exhibitionTitle,
                            visitedDate = item.visitedDate,
                            thumbnailUrl = item.posterUrl,
                            content = item.content,
                            onDeleteClick = { onIntent(MyReviewsIntent.DeleteReviewClicked(item.id)) },
                            onEditedClick = { onIntent(MyReviewsIntent.EditReviewClicked(item)) },
                        )
                    }
                }
            }
        }
    }
    RemoveReviewDialog(
        visible = state.isRemoveDialogVisible,
        onDismissRequest = { onIntent(MyReviewsIntent.RemoveDialogDismissed) },
        onConfirmClick = { onIntent(MyReviewsIntent.RemoveConfirmClicked) },
    )
}

@Composable
private fun ReviewListTopBar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    count: Int,
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
    ) {
        Column(modifier = modifier.padding(vertical = 12.dp)) {
            Text(
                "총 ${count}개",
                modifier = Modifier.padding(horizontal = CONTENT_HORIZONTAL_PADDING),
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
            )
        }
    }
}

@Composable
private fun ReviewItem(
    title: String,
    visitedDate: String,
    thumbnailUrl: String?,
    content: String,
    onDeleteClick: () -> Unit,
    onEditedClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    title,
                    style = AppTextStyle.Body01Bold,
                    color = AppColor.TextPrimary,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "$visitedDate 방문",
                    style = AppTextStyle.Body02Regular,
                    color = AppColor.TextTertiary,
                )
                Spacer(Modifier.height(8.dp))
                Row {
                    ReviewButton(
                        text = "삭제하기",
                        onClick = onDeleteClick,
                    )
                    Spacer(Modifier.width(4.dp))
                    ReviewButton(
                        text = "수정하기",
                        onClick = onEditedClick,
                    )
                }
            }

            ExhibitionThumb(url = thumbnailUrl)
        }
        Spacer(Modifier.height(12.dp))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        AppColor.SubLightGray,
                        shape = RoundedCornerShape(8.dp),
                    ).padding(12.dp),
        ) {
            Text(
                content,
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPrimary,
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
    val shape = RoundedCornerShape(4.dp)
    val size = 72.dp

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

@Composable
private fun RemoveReviewDialog(
    visible: Boolean = false,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    AppDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        primaryText = "삭제하기",
        onPrimaryClick = onConfirmClick,
        primaryEnabled = true,
        secondaryText = "취소",
        onSecondaryClick = onDismissRequest,
        secondaryEnabled = true,
        contentTopPadding = 40.dp,
        contentBottomPadding = 20.dp,
        content = {
            Text(
                "리뷰를 삭제하시겠습니까?",
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text =
                    "삭제한 리뷰는 복구할 수 없으며,\n" +
                        "스탬프도 함께 삭제됩니다.",
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextTertiary,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(16.dp))
        },
    )
}
