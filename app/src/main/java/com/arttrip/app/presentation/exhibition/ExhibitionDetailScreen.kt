package com.arttrip.app.presentation.exhibition

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.arttrip.app.R
import com.arttrip.app.core.ui.component.appbar.AppTopBar
import com.arttrip.app.core.ui.component.button.AppIconButton
import com.arttrip.app.core.ui.component.button.HeartButton
import com.arttrip.app.core.ui.component.dialog.AppTwoButtonDialog
import com.arttrip.app.core.ui.component.image.AppImagePlaceholder
import com.arttrip.app.core.ui.component.image.AppImagePlaceholderType
import com.arttrip.app.core.ui.component.skeleton.StaticSkeleton
import com.arttrip.app.core.ui.component.tab.AppTabCase
import com.arttrip.app.core.ui.component.tab.AppTabRow
import com.arttrip.app.core.ui.component.tag.AppTagL
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.domain.model.review.ExhibitionReview
import com.arttrip.app.presentation.exhibition.contract.ExhibitionDetailIntent
import com.arttrip.app.presentation.exhibition.contract.ExhibitionDetailState
import com.arttrip.app.presentation.exhibition.ui.ExhibitionInfoSection
import com.arttrip.app.presentation.exhibition.ui.tab.ExhibitionDetailInfoTab
import com.arttrip.app.presentation.exhibition.ui.tab.ExhibitionMapTab
import com.arttrip.app.presentation.exhibition.ui.tab.exhibitionReviewTab

@Composable
fun ExhibitionDetailScreen(
    innerPadding: PaddingValues,
    state: ExhibitionDetailState,
    onIntent: (ExhibitionDetailIntent) -> Unit,
    reviewItems: LazyPagingItems<ExhibitionReview>,
) {
    val heroVisibleHeight = 264.dp
    val contentRadius = 16.dp

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

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
                    1 -> item { ExhibitionMapTab(latitude = state.detail.hallLatitude, longitude = state.detail.hallLongitude) }
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

        AppTwoButtonDialog(
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
    if (isLoading) {
        StaticSkeleton(modifier = Modifier.height(height))
        return
    }
    Box(
        modifier = modifier.fillMaxWidth().height(height),
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.matchParentSize(),
            model = url,
            contentDescription = "전시 상세 이미지",
            contentScale = ContentScale.Crop,
            loading = {
                StaticSkeleton(modifier = Modifier.fillMaxSize())
            },
            error = {
                AppImagePlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    type = AppImagePlaceholderType.Wide,
                )
            },
        )
        Box(
            modifier = Modifier.align(Alignment.TopStart).padding(start = 24.dp, top = 16.dp),
        ) {
            chip()
        }
    }
}
