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
import coil.compose.SubcomposeAsyncImage
import com.arttrip.android.core.ui.component.button.ReviewButton
import com.arttrip.android.core.ui.component.skeleton.StaticSkeleton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.domain.model.exhibit.ExhibitionDetailModel

private const val DUMMY_URL = "https://i.ibb.co/nsRDL64B/detail.png"

data class ReviewUiModel(
    val userName: String,
    val date: String, // "2025-11-12"
    val content: String,
    val photoUrls: List<String>,
)

private var dummyReviews =
    listOf(
        ReviewUiModel(
            userName = "김하늘",
            date = "2025-11-12",
            content = "전시 동선이 깔끔하고 작품 설명이 친절해서 몰입하기 좋았어요. 사진 찍기 좋은 포인트도 많았습니다.",
            photoUrls = listOf(DUMMY_URL),
        ),
        ReviewUiModel(
            userName = "파라오의 이집트",
            date = "2025-10-28",
            content = "평일 오전이라 한적해서 여유롭게 봤어요. 다만 공간이 생각보다 작아서 금방 끝났습니다.",
            photoUrls = emptyList(), // 0개
        ),
        ReviewUiModel(
            userName = "이서연",
            date = "2025-09-03",
            content = "테마가 명확해서 좋았고, 마지막 섹션이 특히 인상 깊었어요. 굿즈샵도 꽤 알찼습니다.",
            photoUrls = List(5) { DUMMY_URL },
        ),
        ReviewUiModel(
            userName = "이서연",
            date = "2025-09-03",
            content = "테마가 명확해서 좋았고, 마지막 섹션이 특히 인상 깊었어요. 굿즈샵도 꽤 알찼습니다.",
            photoUrls = List(2) { DUMMY_URL },
        ),
        ReviewUiModel(
            userName = "이서연",
            date = "2025-09-03",
            content = "테마가 명확해서 좋았고, 마지막 섹션이 특히 인상 깊었어요. 굿즈샵도 꽤 알찼습니다.",
            photoUrls = List(3) { DUMMY_URL },
        ),
    )

@Composable
fun ExhibitionReviewTab(detail: ExhibitionDetailModel) {
    if (dummyReviews.isEmpty()) {
        ReviewsEmptyState()
    } else {
        ReviewsContent()
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
private fun ReviewsContent() {
    Column {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(AppColor.SubLightGray)
                    .padding(horizontal = 24.dp, vertical = 12.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                Row {
                    Text(
                        text = "전시 리뷰",
                        style = AppTextStyle.Body01Bold,
                        color = AppColor.TextPrimary,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "(280)",
                        style = AppTextStyle.Body01Bold,
                        color = AppColor.TextPoint,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                ReviewButton(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    onClick = {},
                    text = "리뷰쓰기",
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // TODO 무한스크롤 적용시 LazyColumn으로 변경
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            dummyReviews.forEach { model ->
                ReviewListItem(m = model)
            }
        }
    }
}

@Composable
private fun ReviewListItem(
    modifier: Modifier = Modifier,
    m: ReviewUiModel,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = m.userName,
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextTertiary,
            )
            Text(
                text = m.date,
                style = AppTextStyle.Body01Light,
                color = AppColor.TextTertiary,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier =
                Modifier
                    .padding(horizontal = 24.dp),
            text = m.content,
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
        )
        if (m.photoUrls.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))

            LazyRow(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding =
                    PaddingValues(
                        start = 24.dp,
                        end = 24.dp,
                    ),
            ) {
                items(
                    count = m.photoUrls.size,
                    key = { index -> "${m.photoUrls[index]}#$index" },
                ) { index ->
                    val url = m.photoUrls[index]
                    ReviewPhotoThumbnail(photoUrl = url)
                }
            }
        }

        Spacer(modifier = Modifier.height(19.dp))
        HorizontalDivider(
            modifier =
                Modifier
                    .padding(horizontal = 24.dp),
            thickness = 1.dp,
            color = AppColor.Gray50,
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
        loading = {
            StaticSkeleton(
                modifier =
                    Modifier
                        .fillMaxSize(),
            )
        },
        error = {
            StaticSkeleton(
                modifier =
                    Modifier
                        .fillMaxSize(),
            )
        },
    )
}
