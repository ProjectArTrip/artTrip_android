package com.arttrip.android.core.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable

/**
 * - 상태: selected(on) / unselected(off)
 */
enum class AppFilterChipCase {
    Case01,
    Case02,
}

/**### Figma: Btn case01/02*/
@Composable
fun AppFilterChip(
    modifier: Modifier = Modifier,
    case: AppFilterChipCase = AppFilterChipCase.Case01,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        shape = CircleShape,
        color = chipContainer(selected),
        contentColor = chipContent(selected),
        border = chipBorder(selected),
        modifier = modifier.wrapContentSize().noRippleClickable{onClick()},
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(chipPadding(case)),
        ) {
            Text(
                text = text,
                style = chipTextStyle(selected),
                color = chipContent(selected),
            )
        }
    }
}

@Composable
private fun chipPadding(case: AppFilterChipCase) =
    when (case) {
        AppFilterChipCase.Case01 -> PaddingValues(horizontal = 24.dp, vertical = 12.dp)
        AppFilterChipCase.Case02 -> PaddingValues(horizontal = 20.dp, vertical = 8.dp)
    }

@Composable
private fun chipContainer(selected: Boolean) = if (selected) AppColor.Primary300 else AppColor.Gray0

@Composable
private fun chipContent(selected: Boolean) = if (selected) AppColor.TextWhite else AppColor.TextPrimary

@Composable
private fun chipBorder(selected: Boolean) = if (selected) null else BorderStroke(1.dp, AppColor.Gray100)

@Composable
private fun chipTextStyle(selected: Boolean) = if (selected) AppTextStyle.Body01Bold else AppTextStyle.Body01Light

@Preview(
    name = "FilterChip Preview (Interactive)",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun PreviewAppFilterChip_Interactive() {
    var selectedL by remember { mutableStateOf(false) }
    var selectedS by remember { mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier,
    ) {
        AppFilterChip(
            text = "Button",
            selected = selectedL,
            case = AppFilterChipCase.Case01,
            onClick = { selectedL = !selectedL },
        )

        AppFilterChip(
            text = "Btn",
            selected = selectedS,
            case = AppFilterChipCase.Case02,
            onClick = { selectedS = !selectedS },
        )
    }
}
