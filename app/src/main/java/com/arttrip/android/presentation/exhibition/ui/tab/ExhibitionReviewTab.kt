package com.arttrip.android.presentation.exhibition.ui.tab

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.arttrip.android.core.ui.component.button.ReviewButton
import com.arttrip.android.core.ui.component.skeleton.StaticSkeleton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.domain.model.review.Review

fun LazyListScope.exhibitionReviewTab(
    reviewTotalCount: Int,
    reviews: LazyPagingItems<Review>,
    onWriteReviewClicked: () -> Unit,
) {
    item {
        ReviewsHeaderCard(
            totalCountText = reviewTotalCount,
            onWriteReview = onWriteReviewClicked,
        )
    }

    if (reviewTotalCount == 0) {
        item { ReviewsEmptyState() }
    } else {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(
            count = reviews.itemCount,
            key = { idx -> reviews[idx]?.id ?: idx },
        ) { idx ->
            if (idx > 0) Spacer(modifier = Modifier.height(20.dp))
            val item = reviews[idx] ?: return@items
            ReviewListItem(model = item)
        }
    }
}

@Composable
private fun ReviewsHeaderCard(
    totalCountText: Int,
    onWriteReview: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AppColor.SubLightGray)
                .padding(horizontal = 24.dp, vertical = 12.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row {
                Text(
                    text = "전시 리뷰",
                    style = AppTextStyle.Body01Bold,
                    color = AppColor.TextPrimary,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "($totalCountText)",
                    style = AppTextStyle.Body01Bold,
                    color = AppColor.TextPoint,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ReviewButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onWriteReview,
                text = "리뷰쓰기",
            )
        }
    }
}

@Composable
private fun ReviewListItem(
    modifier: Modifier = Modifier,
    model: Review,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = model.writer,
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextTertiary,
            )
            Text(
                text = model.visitDate,
                style = AppTextStyle.Body01Light,
                color = AppColor.TextTertiary,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = model.content,
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
        )

        if (model.photoUrls.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(start = 24.dp, end = 24.dp),
            ) {
                items(
                    count = model.photoUrls.size,
                    key = { index -> "${model.photoUrls[index]}#$index" },
                ) { index ->
                    ReviewPhotoThumbnail(photoUrl = model.photoUrls[index])
                }
            }
        }

        Spacer(modifier = Modifier.height(19.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 24.dp),
            thickness = 1.dp,
            color = AppColor.Gray50,
        )
    }
}

@Composable
private fun ReviewsEmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 55.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "아직 작성된 리뷰가 없습니다.",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextTertiary,
        )
    }
}

@Composable
private fun ReviewPhotoThumbnail(
    modifier: Modifier = Modifier,
    photoUrl: String,
) {
    SubcomposeAsyncImage(
        modifier =
            modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
        model = photoUrl,
        contentDescription = "리뷰 이미지",
        contentScale = ContentScale.Crop,
        loading = { StaticSkeleton(modifier = Modifier.fillMaxSize()) },
        error = { StaticSkeleton(modifier = Modifier.fillMaxSize()) },
    )
}
