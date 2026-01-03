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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.component.button.AppButton
import com.arttrip.android.core.ui.component.button.AppButtonDefaults
import com.arttrip.android.core.ui.component.button.AppFilterChip
import com.arttrip.android.core.ui.component.button.AppFilterChipCase
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.domain.model.userkeyword.KeywordModel
import com.arttrip.android.presentation.intro.contract.IntroIntent
import com.arttrip.android.presentation.intro.contract.IntroState

@Composable
fun IntroScreen(
    innerPadding: PaddingValues,
    state: IntroState,
    onIntent: (IntroIntent) -> Unit,
) {
    val buttonBottomMargin = 16.dp
    val bottomInset = AppButtonDefaults.Height + buttonBottomMargin
    val bottomContentPadding = 32.dp
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
                        top = 40.dp,
                        end = 24.dp,
                        bottom = bottomInset,
                    ),
        ) {
            IntroWelcomeSection(
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))

            IntroGenreSection(
                modifier = Modifier.fillMaxWidth(),
                genreList = state.genres,
                selectedIds = state.selectedGenreIds,
                onToggleGenre = { id ->
                    onIntent(IntroIntent.ToggleGenre(id))
                },
            )

            Spacer(modifier = Modifier.height(32.dp))

            IntroStyleSection(
                modifier = Modifier.fillMaxWidth(),
                styleList = state.styles,
                selectedIds = state.selectedStyleIds,
                onToggleStyle = { id ->
                    onIntent(IntroIntent.ToggleStyle(id))
                },
            )

            Spacer(modifier = Modifier.height(bottomContentPadding))
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
            enabled = state.isNextEnabled,
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
                "사용자님의 관심있는 키워드를 \n" +
                    "골라주세요!",
            style = AppTextStyle.Headline,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "한 가지 이상 선택이 가능해요.",
            style = AppTextStyle.Body01Light,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(7.dp))
        HorizontalDivider(color = AppColor.Gray100, thickness = 1.dp)
    }
}

@Composable
private fun IntroGenreSection(
    modifier: Modifier = Modifier,
    selectedIds: Set<Int>,
    genreList: List<KeywordModel>,
    onToggleGenre: (Int) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text =
                buildAnnotatedString {
                    append("좋아하는 전시 장르는 무엇인가요? ")
                    withStyle(
                        style = SpanStyle(color = AppColor.SubRed),
                    ) {
                        append("*")
                    }
                },
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(16.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            itemVerticalAlignment = Alignment.Top,
        ) {
            genreList.forEach { genre ->
                val selected = genre.id in selectedIds
                AppFilterChip(
                    case = AppFilterChipCase.Case01,
                    text = genre.name,
                    selected = selected,
                    onClick = { onToggleGenre(genre.id) },
                )
            }
        }
    }
}

@Composable
private fun IntroStyleSection(
    modifier: Modifier = Modifier,
    selectedIds: Set<Int>,
    styleList: List<KeywordModel>,
    onToggleStyle: (Int) -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = "전시 스타일을 골라 주세요",
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.height(16.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            styleList.map { style ->
                val selected = style.id in selectedIds
                AppFilterChip(
                    case = AppFilterChipCase.Case01,
                    text = style.name,
                    selected = selected,
                    onClick = { onToggleStyle(style.id) },
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
