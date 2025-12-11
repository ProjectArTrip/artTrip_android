package com.arttrip.android.presentation.exhibition

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppButton
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.tab.AppTabCase
import com.arttrip.android.core.ui.component.tab.AppTabRow
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailIntent
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailState

@Composable
fun ExhibitionDetailScreen(
    innerPadding: PaddingValues,
    state: ExhibitionDetailState,
    onIntent: (ExhibitionDetailIntent) -> Unit,
) {
    val dummyUrl = "https://i.ibb.co/nsRDL64B/detail.png"
    val heroVisibleHeight = 264.dp
    val contentRadius = 24.dp
    val heroTotalHeight = heroVisibleHeight + contentRadius

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = AppColor.Gray0)
                .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppTopBar(
            showBackButton = true,
            onBackClick = {},
            actions = {
                AppIconButton(
                    iconResId = R.drawable.ic_share_24,
                    contentDescription = "공유",
                )
                AppIconButton(
                    iconResId = R.drawable.ic_heart_outline_black_24,
                    contentDescription = "하트",
                )
            },
        )

        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(AppColor.Gray0),
        ) {
            ExhibitHeroImage(
                url = dummyUrl,
                modifier = Modifier.align(Alignment.TopCenter),
                height = heroTotalHeight,
            )

            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = heroVisibleHeight),
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    topStart = contentRadius,
                                    topEnd = contentRadius,
                                ),
                            ).background(AppColor.Gray0),
                ) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth(),
                    ) {
                        ExhibitionInfoSection()
                        AppTabRow(
                            case = AppTabCase.Case04,
                            tabs = listOf("상세 정보", "지도", "리뷰"),
                            selectedIndex = selectedTabIndex,
                            onTabSelected = { index ->
                                selectedTabIndex = index
                            },
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        ExhibitionDetailTabContent(
                            selectedTabIndex = selectedTabIndex,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExhibitHeroImage(
    modifier: Modifier = Modifier,
    url: String,
    height: Dp,
) {
    SubcomposeAsyncImage(
        modifier =
            modifier
                .fillMaxWidth()
                .height(height),
        model = url,
        contentDescription = "전시 상세 이미지",
        contentScale = ContentScale.Crop,
        loading = {
            ShimmerSkeletonBox(
                modifier =
                    Modifier
                        .fillMaxSize(),
            )
        },
        error = {
            StaticSkeletonBox(
                modifier =
                    Modifier
                        .fillMaxSize(),
            )
        },
    )
}

@Composable
private fun ExhibitionInfoSection() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
    ) {
        Text(
            text = "메이지·다이쇼 시대 예술의 장식적 취향을 통해 본 아르누보와 그 주변 환경",
            style = AppTextStyle.Headline,
            color = AppColor.TextPrimary,
            textAlign = TextAlign.Start,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "다케히사 유메지 미술관",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
            textAlign = TextAlign.Start,
        )
        Text(
            text = "2025.06.07 - 2025.09.14",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(
            onClick = {},
            enabled = true,
            text = "홈페이지 바로가기",
        )
    }
}

@Composable
private fun ExhibitionDetailTabContent(selectedTabIndex: Int) {
    when (selectedTabIndex) {
        0 -> ExhibitionDetailInfoContent()
        1 -> ExhibitionMapContent()
        2 -> ExhibitionReviewContent()
    }
}

@Composable
private fun ExhibitionDetailInfoContent() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(AppColor.Gray50)
                .height(300.dp)
                .padding(16.dp),
    ) {
        Text(
            text = "상세 정보 탭 컨텐츠 영역 (임시)",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
        )
    }
}

@Composable
private fun ExhibitionMapContent() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(AppColor.Gray50)
                .height(300.dp)
                .padding(16.dp),
    ) {
        Text(
            text = "지도 탭 컨텐츠 영역 (임시)",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
        )
    }
}

@Composable
private fun ExhibitionReviewContent() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(AppColor.Gray50)
                .height(300.dp)
                .padding(16.dp),
    ) {
        Text(
            text = "리뷰 탭 컨텐츠 영역 (임시)",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
        )
    }
}

@Composable
private fun ShimmerSkeletonBox(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = 1200, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            ),
        label = "shimmerProgress",
    )

    val shimmerBrush =
        Brush.linearGradient(
            colors =
                listOf(
                    AppColor.Gray50,
                    AppColor.Gray100,
                ),
            start =
                Offset(
                    x = -200f + 400f * progress,
                    y = 0f,
                ),
            end =
                Offset(
                    x = 200f + 400f * progress,
                    y = 0f,
                ),
        )

    Box(
        modifier =
            modifier
                .background(shimmerBrush),
    )
}

@Composable
private fun StaticSkeletonBox(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .background(AppColor.Gray50),
    )
}

@Preview(showBackground = true, name = "Exhibition Detail Preview")
@Composable
private fun ExhibitionDetailScreenPreview() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        ExhibitionDetailScreen(
            innerPadding = innerPadding,
            state = ExhibitionDetailState(),
            onIntent = {},
        )
    }
}
