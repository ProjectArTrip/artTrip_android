package com.arttrip.android.core.ui.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arttrip.android.R
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.ui.theme.Pretendard
import com.arttrip.android.core.util.noRippleClickable

/**
 * [Default] : 힌트 표시
 *
 * [Clicked] : 선택 대기(포커스)
 *
 * [Filled] : 날짜 값 표시 + 삭제(X) 노출
 */

enum class DateFieldChipState {
    Default,
    Clicked,
    Filled,
}

/**
 * ### Figma: Component
 * 날짜 선택/표시용 필드 칩.
 *
 * - Default/Clicked: value가 비어 있으면 placeholder를 표시
 * - Filled: [value]만 표시하며 X(삭제) 아이콘 노출
 *
 * Clicked → Default 복귀(칩 바깥 터치로 해제) 규칙은
 * 부모(화면)에서 상태를 제어
 *
 * @param value 선택/표시할 날짜 텍스트. [Filled][DateFieldChipState.Filled]상태에서 필수.
 * @param placeholder value가 비어 있고
 * [Default][DateFieldChipState.Default]/[Clicked][DateFieldChipState.Clicked] 상태에서 필수.
 * @param state 칩 상태(Default/Clicked/Filled).
 * @param modifier 외부에서 전달받는 Modifier.
 * @param onChipClick 칩 전체 클릭 시 호출. 일반적으로 날짜 선택 UI를 연다.
 * @param onDismissClick Filled 상태에서 X 클릭 시 호출. 선택된 값을 제거한다.
 */

@Composable
fun DateFieldChip(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    state: DateFieldChipState = DateFieldChipState.Default,
    onChipClick: () -> Unit = {},
    onDismissClick: () -> Unit = {},
) {
    when (state) {
        DateFieldChipState.Default,
        DateFieldChipState.Clicked,
        ->
            require(placeholder.isNotBlank()) {
                "DateFieldChip: state=$state 에서는 placeholder가 필요합니다. placeholder=\"$placeholder\""
            }
        DateFieldChipState.Filled ->
            require(value.isNotBlank()) {
                "DateFieldChip: state=$state 에서는 value가 필요합니다. value=\"$value\""
            }
    }

    val showPlaceholder = state != DateFieldChipState.Filled
    val displayText = if (showPlaceholder) placeholder else value

    val style = resolveDateFieldChipStyle(state, showPlaceholder)
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier =
            modifier
                .height(48.dp)
                .background(Color.White, shape)
                .border(1.dp, style.borderColor, shape)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .noRippleClickable { onChipClick() },
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val showDismiss = state == DateFieldChipState.Filled

            Text(
                text = displayText,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = style.textStyle.copy(color = style.textColor),
            )

            if (showDismiss) {
                Icon(
                    modifier = Modifier.noRippleClickable { onDismissClick() },
                    painter = painterResource(id = R.drawable.ic_close_2_24),
                    contentDescription = "dismiss",
                    tint = Color.Unspecified,
                )
            }
        }
    }
}

@Stable
internal data class DateFieldChipStyle(
    val borderColor: Color,
    val textStyle: TextStyle,
    val textColor: Color,
)

internal object DateFieldChipDefaults {
    val BorderDefault = AppColor.Gray100
    val BorderActive = AppColor.Primary300

    val HintTextStyle = AppTextStyle.Body01Regular
    val FilledTextStyle =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 16.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(700),
        )

    val HintTextColor = AppColor.TextTertiary
    val FilledTextColor = AppColor.TextPrimary
}

private fun resolveDateFieldChipStyle(
    state: DateFieldChipState,
    showPlaceholder: Boolean,
): DateFieldChipStyle {
    val borderColor =
        when (state) {
            DateFieldChipState.Default -> DateFieldChipDefaults.BorderDefault
            DateFieldChipState.Clicked,
            DateFieldChipState.Filled,
            -> DateFieldChipDefaults.BorderActive
        }

    val (textStyle, textColor) =
        if (showPlaceholder || state != DateFieldChipState.Filled) {
            DateFieldChipDefaults.HintTextStyle to DateFieldChipDefaults.HintTextColor
        } else {
            DateFieldChipDefaults.FilledTextStyle to DateFieldChipDefaults.FilledTextColor
        }

    return DateFieldChipStyle(
        borderColor = borderColor,
        textStyle = textStyle,
        textColor = textColor,
    )
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun PreviewDateFieldChip_Interaction() {
    var leftText by remember { mutableStateOf("") }
    var leftState by remember { mutableStateOf(DateFieldChipState.Default) }
    var rightText by remember { mutableStateOf("") }
    var rightState by remember { mutableStateOf(DateFieldChipState.Default) }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    // Clicked이면 Default로 복귀
                    if (leftState == DateFieldChipState.Clicked) leftState = DateFieldChipState.Default
                    if (rightState == DateFieldChipState.Clicked) rightState = DateFieldChipState.Default
                },
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DateFieldChip(
                modifier = Modifier.weight(1f),
                value = leftText,
                placeholder = "시작일",
                state = leftState,
                onChipClick = {
                    if (leftState == DateFieldChipState.Default) {
                        leftState = DateFieldChipState.Clicked
                    }
                },
                onDismissClick = {
                    leftState = DateFieldChipState.Default
                    leftText = ""
                },
            )

            DateFieldChip(
                modifier = Modifier.weight(1f),
                value = rightText,
                placeholder = "종료일",
                state = rightState,
                onChipClick = {
                    if (rightState == DateFieldChipState.Default) {
                        rightState = DateFieldChipState.Clicked
                    }
                },
                onDismissClick = {
                    rightState = DateFieldChipState.Default
                    rightText = ""
                },
            )
        }

        Button(
            onClick = {
                if (leftState == DateFieldChipState.Clicked) {
                    leftState = DateFieldChipState.Filled
                    leftText = "11월 25일 화"
                }
                if (rightState == DateFieldChipState.Clicked) {
                    rightState = DateFieldChipState.Filled
                    rightText = "2월 1일 월"
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("채워짐 적용")
        }

        Text("left: $leftState / right: $rightState")
    }
}
