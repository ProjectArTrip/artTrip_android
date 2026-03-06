package com.arttrip.android.presentation.exhibition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.button.HeartButton
import com.arttrip.android.core.ui.component.dialog.AppDialog
import com.arttrip.android.core.ui.component.skeleton.StaticSkeleton
import com.arttrip.android.core.ui.component.tab.AppTabCase
import com.arttrip.android.core.ui.component.tab.AppTabRow
import com.arttrip.android.core.ui.component.tag.AppTagL
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.domain.model.review.ExhibitionReview
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailIntent
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailState
import com.arttrip.android.presentation.exhibition.ui.ExhibitionInfoSection
import com.arttrip.android.presentation.exhibition.ui.tab.ExhibitionDetailInfoTab
import com.arttrip.android.presentation.exhibition.ui.tab.ExhibitionMapTab
import com.arttrip.android.presentation.exhibition.ui.tab.exhibitionReviewTab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun ExhibitionDetailScreen(
    innerPadding: PaddingValues,
    state: ExhibitionDetailState,
    onIntent: (ExhibitionDetailIntent) -> Unit,
    reviewsFlow: Flow<PagingData<ExhibitionReview>>,
) {
    val heroVisibleHeight = 264.dp
    val contentRadius = 16.dp

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val reviewItems = reviewsFlow.collectAsLazyPagingItems()
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(AppColor.Gray0)
                .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppTopBar(
            leading = {
                AppIconButton(
                    iconResId = R.drawable.ic_back_24,
                    contentDescription = "뒤로가기",
                ) {
                    onIntent(ExhibitionDetailIntent.BackClicked)
                }
            },
            actions = {
                HeartButton(isSelected = state.isBookmarked, onClick = {
                    onIntent(ExhibitionDetailIntent.BookmarkClicked)
                })
            },
        )

        if (state.detail == null) {
            ExhibitionDetailLoadingContent()
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                contentPadding = PaddingValues(bottom = 24.dp),
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        ExhibitHeroImage(
                            modifier = Modifier.fillMaxWidth(),
                            height = heroVisibleHeight + contentRadius,
                            url = state.detail.posterUrl,
                            isLoading = state.isLoading,
                            chip = { AppTagL(status = state.detail.status) },
                        )

                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = heroVisibleHeight - contentRadius)
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = contentRadius,
                                            topEnd = contentRadius,
                                        ),
                                    ).background(AppColor.Gray0),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Spacer(modifier = Modifier.height(24.dp))

                                ExhibitionInfoSection(
                                    modifier = Modifier.padding(horizontal = 24.dp),
                                    detail = state.detail,
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                AppTabRow(
                                    modifier = Modifier.padding(horizontal = 24.dp),
                                    case = AppTabCase.Case04,
                                    tabs = listOf("상세 정보", "지도", "리뷰"),
                                    selectedIndex = selectedTabIndex,
                                    onTabSelected = { selectedTabIndex = it },
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
                when (selectedTabIndex) {
                    0 -> item { ExhibitionDetailInfoTab(detail = state.detail) }
                    1 -> item { ExhibitionMapTab() }
                    2 ->
                        exhibitionReviewTab(
                            reviewTotalCount = state.reviewTotalCount ?: 0,
                            reviews = reviewItems,
                            onWriteReviewClicked = {
                                onIntent(
                                    ExhibitionDetailIntent.WriteReviewClicked,
                                )
                            },
                        )
                }
            }
        }

        AppDialog(
            visible = state.writeReviewDialogVisible,
            onDismissRequest = { onIntent(ExhibitionDetailIntent.WriteReviewDialogDismissClicked) },
            primaryText = "리뷰 작성",
            onPrimaryClick = { onIntent(ExhibitionDetailIntent.WriteReviewConfirmClicked) },
            secondaryText = "취소",
            onSecondaryClick = { onIntent(ExhibitionDetailIntent.WriteReviewDialogDismissClicked) },
        ) {
            Text(
                text = "리뷰 쓰고 스탬프 받기",
                style =
                    AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
            )

            Spacer(Modifier.height(11.dp))

            HorizontalDivider(
                thickness = 1.dp,
                color = AppColor.Gray50,
            )

            Spacer(Modifier.height(30.dp))

            Text(
                text = "전시 리뷰를 작성하시면\n해당 국가 스탬프가 자동 발급됩니다.",
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "리뷰를 작성하시겠습니까?",
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun ExhibitionDetailLoadingContent() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(AppColor.Gray100),
        )
        Spacer(Modifier.height(24.dp))
        repeat(3) {
            Box(
                modifier =
                    Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(AppColor.Gray100),
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ExhibitHeroImage(
    modifier: Modifier = Modifier,
    height: Dp,
    url: String?,
    isLoading: Boolean = true,
    chip: @Composable (() -> Unit),
) {
    if (url.isNullOrEmpty() || isLoading) {
        StaticSkeleton(
            modifier =
                Modifier
                    .height(height),
        )
        return
    }
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(height),
    ) {
        SubcomposeAsyncImage(
            modifier =
                modifier
                    .matchParentSize(),
            model = url,
            contentDescription = "전시 상세 이미지",
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
        Box(
            modifier =
                Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 24.dp, top = 16.dp),
        ) {
            chip()
        }
    }
}

@Preview(showBackground = true, name = "Exhibition Detail Preview")
@Composable
private fun ExhibitionDetailScreenPreview() {
    val dummyReviewsFlow =
        flowOf(
            PagingData.from(
                listOf(
                    ExhibitionReview(
                        id = 1,
                        writer = "김하늘",
                        visitDate = "2025-11-12",
                        content = "전시 동선이 깔끔하고 작품 설명이 친절해서 몰입하기 좋았어요.",
                        photoUrls = emptyList(),
                    ),
                    ExhibitionReview(
                        id = 2,
                        writer = "이서연",
                        visitDate = "2025-09-03",
                        content = "테마가 명확해서 좋았고, 마지막 섹션이 특히 인상 깊었어요.",
                        photoUrls = listOf("https://i.ibb.co/nsRDL64B/detail.png"),
                    ),
                ),
            ),
        )

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ExhibitionDetailScreen(
            innerPadding = innerPadding,
            state = ExhibitionDetailState(),
            onIntent = {},
            reviewsFlow = dummyReviewsFlow,
        )
    }
}
