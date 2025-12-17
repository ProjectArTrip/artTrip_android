package com.arttrip.android.core.ui.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable

/**
 * ### Figma: suggestion_text
 *
 * 제안 텍스트 칩 (클릭 가능)
 *
 * @param label 칩에 표시할 텍스트
 * @param modifier 외부에서 전달받는 Modifier
 * @param onChipClick 칩 전체 클릭 시 호출
 */
@Composable
fun SuggestionChip(
    modifier: Modifier = Modifier,
    label: String,
    onChipClick: () -> Unit = {},
) {
    BaseChip(
        modifier = modifier,
        paddingHorizontal = 20.dp,
        paddingVertical = 8.dp,
        onClick = onChipClick,
    ) {
        Text(
            text = label,
            style = AppTextStyle.Body01Bold,
            color = AppColor.TextPoint,
        )
    }
}

/**
 * ### Figma: recent_search
 *
 * 최근 검색 칩 (칩 전체 클릭 + 삭제(X) 클릭 분리)
 *
 * @param label 칩에 표시할 텍스트
 * @param modifier 외부에서 전달받는 Modifier
 * @param onChipClick 칩 전체 클릭 시 호출
 * @param onDismissClick X 아이콘 클릭 시 호출
 */
@Composable
fun RecentSearchChip(
    modifier: Modifier = Modifier,
    label: String,
    onChipClick: () -> Unit = {},
    onDismissClick: () -> Unit = {},
) {
    BaseChip(
        modifier = modifier,
        paddingHorizontal = 12.dp,
        paddingVertical = 8.dp,
        onClick = onChipClick,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(4.dp))
            Text(
                text = label,
                style = AppTextStyle.Body01Light,
                color = AppColor.TextPrimary,
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                modifier = Modifier.noRippleClickable { onDismissClick() },
                painter = painterResource(id = R.drawable.ic_close_1_24),
                contentDescription = "dismiss",
                tint = Color.Unspecified,
            )
        }
    }
}

/**
 * ### Figma: Chip_country
 *
 * 국가/지역 라벨 칩 (클릭 불가)
 *
 * @param modifier 외부에서 전달받는 Modifier
 * @param label 칩에 표시할 텍스트
 */
@Composable
fun CountryChip(
    modifier: Modifier = Modifier,
    label: String,
) {
    BaseChip(
        modifier = modifier,
        height = 22.dp,
        paddingHorizontal = 8.dp,
        paddingVertical = 4.dp,
        containerColor = Color.Black.copy(alpha = 0.6f),
        borderWidth = 0.dp,
        onClick = null,
    ) {
        Text(
            text = label,
            style = AppTextStyle.Body02Bold,
            color = AppColor.TextWhite,
        )
    }
}

@Composable
private fun BaseChip(
    modifier: Modifier = Modifier,
    height: Dp = 40.dp,
    paddingHorizontal: Dp = 12.dp,
    paddingVertical: Dp = 8.dp,
    containerColor: Color = AppColor.Gray0,
    borderWidth: Dp = 1.dp,
    borderColor: Color = AppColor.Gray100,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val clickModifier =
        if (onClick != null) {
            Modifier.noRippleClickable { onClick() }
        } else {
            Modifier
        }

    Box(
        modifier =
            modifier
                .height(height)
                .background(containerColor, CircleShape)
                .then(
                    if (borderWidth > 0.dp) Modifier.border(borderWidth, borderColor, CircleShape) else Modifier,
                ).padding(horizontal = paddingHorizontal, vertical = paddingVertical)
                .then(clickModifier),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChipsAll() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SuggestionChip(label = "Suggestion")
        RecentSearchChip(label = "Recent Search")
        CountryChip(label = "Korea")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuggestionChip() {
    var clickCount by remember { mutableIntStateOf(0) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SuggestionChip(
            label = "Title",
            onChipClick = { clickCount++ },
        )
        Text("clicked: $clickCount")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecentSearchChip() {
    var chipClickCount by remember { mutableIntStateOf(0) }
    var dismissClickCount by remember { mutableIntStateOf(0) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        RecentSearchChip(
            label = "Title",
            onChipClick = { chipClickCount++ },
            onDismissClick = { dismissClickCount++ },
        )
        Text("chip clicked: $chipClickCount")
        Text("dismiss clicked: $dismissClickCount")
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewCountryChip() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        CountryChip(label = "오스트리아")
        CountryChip(label = "프랑스")
        CountryChip(label = "서울")
        CountryChip(label = "제주")
    }
}
