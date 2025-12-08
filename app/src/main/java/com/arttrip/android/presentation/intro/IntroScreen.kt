package com.arttrip.android.presentation.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.component.button.AppButton
import com.arttrip.android.core.ui.component.button.AppFilterChip
import com.arttrip.android.core.ui.component.button.AppFilterChipCase
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.presentation.intro.contract.IntroIntent
import com.arttrip.android.presentation.intro.contract.IntroState

@Composable
fun IntroScreen(
    innerPadding: PaddingValues,
    state: IntroState,
    onIntent: (IntroIntent) -> Unit,
) {
    val isNextEnabled by remember(
        state.selectedGenreIds,
        state.selectedStyleIds,
    ) {
        derivedStateOf {
            state.selectedGenreIds.isNotEmpty() &&
                state.selectedStyleIds.isNotEmpty()
        }
    }

    val buttonHeight = 52.dp
    val buttonBottomMargin = 16.dp

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(AppColor.Gray0)
                .padding(bottom = buttonBottomMargin),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = 24.dp,
                        top = 56.dp,
                        end = 24.dp,
                        bottom = buttonHeight,
                    ),
        ) {
            IntroWelcomeSection(
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(32.dp))

            IntroGenreSection(
                modifier = Modifier.fillMaxWidth(),
                selectedIds = state.selectedGenreIds,
                onToggleGenre = { id ->
                    onIntent(IntroIntent.ToggleGenre(id))
                },
            )

            Spacer(modifier = Modifier.height(40.dp))

            IntroStyleSection(
                modifier = Modifier.fillMaxWidth(),
                selectedIds = state.selectedStyleIds,
                onToggleStyle = { id ->
                    onIntent(IntroIntent.ToggleStyle(id))
                },
            )

            Spacer(modifier = Modifier.height(34.dp))
        }

        IntroBottomCta(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                    ),
            enabled = isNextEnabled,
            onClick = { onIntent(IntroIntent.ClickNext) },
        )
    }
}

@Composable
private fun IntroWelcomeSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text =
                "이유지님의 관심있는 키워드를 \n" +
                    "골라주세요!",
            style = AppTextStyle.Headline,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "아트트립과 전시 여행을 시작해볼까요?",
            style = AppTextStyle.Title02Light,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(11.dp))
        HorizontalDivider(color = AppColor.Gray100, thickness = 1.dp)
    }
}

@Composable
private fun IntroGenreSection(
    modifier: Modifier = Modifier,
    selectedIds: List<Int>,
    genreList: List<Pair<Int, String>> =
        listOf( // TODO API로 변경
            1 to "현대미술",
            2 to "사진전",
            3 to "디지털아트",
            4 to "고대미술",
            5 to "조각",
            6 to "설치미술",
            7 to "공예",
            8 to "회화",
            9 to "건축",
        ),
    onToggleGenre: (Int) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "좋아하는 전시 장르는 무엇인가요?",
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(20.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            itemVerticalAlignment = Alignment.Top,
        ) {
            genreList.forEach { genre ->
                val selected = genre.first in selectedIds
                AppFilterChip(
                    case = AppFilterChipCase.Case01,
                    text = genre.second,
                    selected = selected,
                    onClick = { onToggleGenre(genre.first) },
                )
            }
        }
    }
}

@Composable
private fun IntroStyleSection(
    modifier: Modifier = Modifier,
    selectedIds: List<Int>,
    styleList: List<Pair<Int, String>> =
        listOf(
            1 to "인터랙티브",
            2 to "공간연출",
            3 to "몰입형",
            4 to "팝업",
            5 to "미디어아트",
            6 to "사운드 기반",
            7 to "VR",
            8 to "AR",
        ),
    onToggleStyle: (Int) -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = "전시 스타일을 골라 주세요",
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(20.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            styleList.map { style ->
                val selected = style.first in selectedIds
                AppFilterChip(
                    case = AppFilterChipCase.Case01,
                    text = style.second,
                    selected = selected,
                    onClick = { onToggleStyle(style.first) },
                )
            }
        }
    }
}

@Composable
private fun IntroBottomCta(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit = {},
) {
    AppButton(
        modifier = modifier,
        text = "다음으로",
        onClick = onClick,
        enabled = enabled,
    )
}

@Preview(
    name = "IntroScreen",
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun PreviewIntroScreen() {
    IntroScreen(
        innerPadding = PaddingValues(0.dp),
        state = IntroState(),
        onIntent = {},
    )
}
