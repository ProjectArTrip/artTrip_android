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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable

/**
 * ### Figma: suggestion_text
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
        paddingHorizontal = 20,
        paddingVertical = 8,
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
 * 삭제(X) 액션이 있는 칩. 칩 전체/삭제 아이콘 클릭을 분리
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
        paddingHorizontal = 12,
        paddingVertical = 8,
        onClick = onChipClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
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

@Composable
private fun BaseChip(
    modifier: Modifier = Modifier,
    paddingHorizontal: Int = 12,
    paddingVertical: Int = 8,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            modifier
                .height(40.dp)
                .background(AppColor.Gray0, CircleShape)
                .border(1.dp, AppColor.Gray100, CircleShape)
                .padding(horizontal = paddingHorizontal.dp, vertical = paddingVertical.dp)
                .noRippleClickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewSuggestionChip() {
    var clickCount by remember { mutableStateOf(0) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SuggestionChip(
            label = "Title",
            onChipClick = { clickCount++ },
        )
        Text("clicked: $clickCount")
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewRecentSearchChip() {
    var chipClickCount by remember { mutableStateOf(0) }
    var dismissClickCount by remember { mutableStateOf(0) }

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
