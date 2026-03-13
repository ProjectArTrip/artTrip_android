package com.arttrip.android.presentation.home.sub.genre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.exhibition.ExhibitionStatus
import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppButton
import com.arttrip.android.core.ui.component.button.AppFilterChip
import com.arttrip.android.core.ui.component.button.AppFilterChipCase
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.button.LikeButton
import com.arttrip.android.core.ui.component.sheet.AppBottomSheetTopBar
import com.arttrip.android.core.ui.component.sheet.AppModalBottomSheet
import com.arttrip.android.core.ui.component.tag.AppTag
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.presentation.home.ExhibitionImage
import com.arttrip.android.presentation.home.ExhibitionImageCase
import com.arttrip.android.presentation.home.sub.genre.contract.GenreIntent
import com.arttrip.android.presentation.home.sub.genre.contract.GenreState

@Composable
fun GenreScreen(
    innerPadding: PaddingValues,
    state: GenreState,
    onIntent: (GenreIntent) -> Unit,
    country: ForeignCountry?,
    genre: ExhibitionGenre,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Spacer(
                modifier =
                    Modifier
                        .height(16.dp),
            )
            AppTopBar(
                title = "${state.selectedGenre?.label} 전시 추천",
                leading = {
                    AppIconButton(
                        iconResId = R.drawable.ic_back_24,
                        contentDescription = "Back Button",
                        onIconClick = {
                            onIntent(GenreIntent.BackClicked)
                        },
                    )
                },
                actions = {
                    AppIconButton(
                        iconResId = R.drawable.ic_alert_badge_24,
                        contentDescription = "Notification Button",
                        onIconClick = {
                            onIntent(GenreIntent.NotificationIconClicked)
                        },
                    )
                },
            )
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "총 N개",
                    style = AppTextStyle.Title02Bold,
                    color = AppColor.TextPrimary,
                )
                AppIconButton(
                    iconResId = R.drawable.ic_filter_24,
                    contentDescription = "Filter Button",
                    onIconClick = {
                        onIntent(GenreIntent.OpenFilterSheet)
                    },
                )
            }
            ExhibitionList(
                onExhibitionClick = {},
                onLikeClick = {},
            )
        }
    }
    GenreFilterBottomSheet(
        visible = state.isFilterSheetVisible,
        onIntent = onIntent,
        state = state
    )
}

@Composable
fun GenreFilterBottomSheet(
    visible : Boolean,
    onIntent: (GenreIntent) -> Unit,
    state: GenreState
) {
    var selectedSortType by remember { mutableStateOf(state.selectedSortType) }
    var selectedGenre by remember { mutableStateOf(state.selectedGenre) }

    AppModalBottomSheet(
        visible = visible,
        topBar = AppBottomSheetTopBar.Header(),
        onDismissRequest = {
            onIntent(GenreIntent.CloseFilterSheet)
        },
        content = {
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            Text("정렬",
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPrimary)
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SortType.entries.forEach { sortType ->
                    AppFilterChip(
                        case = AppFilterChipCase.Case02,
                        text = sortType.label,
                        selected = selectedSortType == sortType,
                        onClick = {
                            selectedSortType = sortType
                        }
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = AppColor.Gray100)
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Text("전시 장르",
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPrimary)
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ExhibitionGenre.entries.forEach { genre ->
                    AppFilterChip(
                        case = AppFilterChipCase.Case02,
                        text = genre.label,
                        selected = selectedGenre == genre,
                        onClick = {
                            selectedGenre = genre
                        }
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(48.dp)
            )
            AppButton(
                text = "적용하기",
                onClick = {
                    onIntent(GenreIntent.CloseFilterSheet)
                    onIntent(GenreIntent.SelectSortType(selectedSortType))
                    onIntent(GenreIntent.SelectGenre(selectedGenre!!))
                }
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
        }
    )
}

@Composable
fun ExhibitionList(
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
        ) {
            val dummyExhibition =
                Exhibition(
                    id = 1,
                    title = "DDP 매거진 라이브러리: 기록에 머물다",
                    posterUrl = "https://i.pinimg.com/originals/5d/90/1f/5d901f30a1ee270123e19b1404165113.jpg",
                    status = ExhibitionStatus.ONGOING,
                    period = "2025.01.01 - 2025.12.31",
                    hallName = "DDP 동대문디자인플라자",
                    country = "대한민국",
                    region = "서울",
                    isBookmarked = true,
                )
            Spacer(
                modifier =
                    Modifier
                        .height(8.dp),
            )
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                repeat(10) {
                    ExhibitionItem(
                        exhibition = dummyExhibition,
                        onExhibitionClick = { id ->
                            onExhibitionClick(id)
                        },
                        onLikeClick = { id ->
                            onLikeClick(id)
                        },
                    )
                }
            }
            Spacer(
                modifier =
                    Modifier
                        .height(24.dp),
            )
        }
    }
}

@Composable
fun ExhibitionItem(
    exhibition: Exhibition,
    onExhibitionClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .noRippleClickable {
                    onExhibitionClick(exhibition.id)
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ExhibitionImage(
            url = exhibition.posterUrl,
            case = ExhibitionImageCase.CASE3,
        ) {
            LikeButton(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = (8).dp),
                isSelected = exhibition.isBookmarked,
            ) {
                onLikeClick(exhibition.id)
            }
            AppTag(
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd),
                status = exhibition.status,
            )
        }
        Spacer(
            modifier =
                Modifier
                    .width(12.dp),
        )
        Column {
            Text(
                text = exhibition.title,
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPrimary,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(4.dp),
            )
            Text(
                text = exhibition.hallName,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextTertiary,
            )
            Spacer(
                modifier =
                    Modifier
                        .height(2.dp),
            )
            Text(
                text = exhibition.period,
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextTertiary,
            )
        }
    }
}
