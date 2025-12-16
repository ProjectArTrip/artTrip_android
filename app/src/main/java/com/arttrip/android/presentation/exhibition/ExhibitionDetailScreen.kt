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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.skeleton.StaticSkeleton
import com.arttrip.android.core.ui.component.tab.AppTabCase
import com.arttrip.android.core.ui.component.tab.AppTabRow
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailIntent
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailState
import com.arttrip.android.presentation.exhibition.ui.ExhibitionDetailTabSection
import com.arttrip.android.presentation.exhibition.ui.ExhibitionInfoSection

@Composable
fun ExhibitionDetailScreen(
    innerPadding: PaddingValues,
    state: ExhibitionDetailState,
    onIntent: (ExhibitionDetailIntent) -> Unit,
) {
    val heroVisibleHeight = 264.dp
    val contentRadius = 16.dp

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(AppColor.Gray0)
                .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppTopBar(
            showBackButton = true,
            onBackClick = { onIntent(ExhibitionDetailIntent.BackClicked) },
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

        if (state.detail == null) {
            ExhibitionDetailLoadingContent()
        } else {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    ExhibitHeroImage(
                        modifier = Modifier.fillMaxWidth(),
                        height = heroVisibleHeight + contentRadius,
                        url = state.detail?.posterUrl,
                        isLoading = state.isLoading,
                    )

                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(top = heroVisibleHeight - contentRadius)
                                .clip(RoundedCornerShape(topStart = contentRadius, topEnd = contentRadius))
                                .background(AppColor.Gray0),
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

                            ExhibitionDetailTabSection(selectedTabIndex = selectedTabIndex, detail = state.detail)

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
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
) {
    if (url.isNullOrEmpty() || isLoading) {
        StaticSkeleton(
            modifier =
                Modifier
                    .height(height),
        )
        return
    }
    SubcomposeAsyncImage(
        modifier =
            modifier
                .fillMaxWidth()
                .height(height),
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
