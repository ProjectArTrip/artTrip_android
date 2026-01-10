package com.arttrip.android.core.ui.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable

/**
 * ### 공용 입력 컴포넌트: 입력형
 *
 * - [BasicTextField] 기반의 **단일** 라인 입력 UI
 * - [placeholder] 지원
 * - 우측 [trailing] 슬롯(아이콘/버튼) 지원
 * - [isError] 시 보더 컬러 변경
 *
 * 상태별 동작 / 텍스트 컬러
 * - enabled = false
 *   · 입력/포커스 불가 (비활성)
 *   · Text color: TextPrimary
 *
 * - readOnly = true
 *   · 활성 상태 유지, 편집만 불가
 *   · Text color: TextTertiary
 *
 * 예시:
 * ```
 * var text by remember { mutableStateOf("") }
 *
 * AppTextField(
 *   value = text,
 *   onValueChange = { text = it },
 *   placeholder = "닉네임을 입력하세요",
 * )
 * ```
 */

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    enabled: Boolean = true,
    isError: Boolean = false,
    placeholder: String? = null,
    textStyle: TextStyle = AppTextStyle.Body01Regular,
    trailing: (@Composable (() -> Unit))? = null,
) {
    val textColor = if (enabled) AppColor.TextPrimary else AppColor.TextTertiary

    InputContainer(
        modifier = modifier,
        isError = isError,
        enabled = enabled,
        onClick = null,
        trailing = trailing,
    ) { contentModifier ->
        BasicTextField(
            modifier = contentModifier,
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            singleLine = true,
            textStyle = textStyle.copy(color = textColor),
            decorationBox = { inner ->
                if (value.isEmpty() && !placeholder.isNullOrBlank()) {
                    Text(
                        text = placeholder,
                        style = textStyle,
                        color = AppColor.TextTertiary,
                    )
                }
                inner()
            },
        )
    }
}

/**
 * ### 공용 입력 컴포넌트: 선택(클릭)형
 *
 * - 키보드 입력 없이, 전체 영역 클릭(`onClick`) 지원
 * - 값이 없으면 [placeholder]를 표시
 * - 우측 [trailing] 슬롯(아이콘/버튼) 지원
 * - [isError] 시 보더 컬러 변경
 *
 * ```
 * var selected by remember { mutableStateOf<String?>(null) }
 *
 * AppSelectField(
 *   text = selected,
 *   placeholder = "방문일을 선택하세요",
 *   onClick = { selected = "11월 25일 화" },
 *   trailing = { AppIconButton(iconResId = R.drawable.ic_calendar_24) },
 * )
 * ```
 */
@Composable
fun AppSelectField(
    modifier: Modifier = Modifier,
    text: String?,
    placeholder: String,
    enabled: Boolean = true,
    isError: Boolean = false,
    onClick: () -> Unit,
    trailing: (@Composable (() -> Unit))? = null, // 오른쪽 아이콘/버튼 슬롯
) {
    val hasValue = !text.isNullOrBlank()

    InputContainer(
        modifier = modifier,
        enabled = enabled,
        isError = isError,
        onClick = onClick,
        trailing = trailing,
    ) { contentModifier ->
        Text(
            modifier = contentModifier,
            text = if (hasValue) text else placeholder,
            style = AppTextStyle.Body01Regular,
            color = if (hasValue) AppColor.TextPrimary else AppColor.TextTertiary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Stable
private data class InputContainerColors(
    val background: Color,
    val border: Color,
)

private object InputContainerDefaults {
    val Height: Dp = 48.dp
    val Shape = RoundedCornerShape(8.dp)
    val Padding = 16.dp

    fun colors(isError: Boolean): InputContainerColors =
        if (isError) {
            InputContainerColors(
                background = AppColor.Gray0,
                border = AppColor.SubRed,
            )
        } else {
            InputContainerColors(
                background = AppColor.Gray0,
                border = AppColor.Gray100,
            )
        }
}

/**
 * 내부 공용 컨테이너.
 *
 * - 입력/선택 컴포넌트의 공통 외형(높이/패딩/보더/배경)을 제공
 */
@Composable
private fun InputContainer(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    trailing: (@Composable (() -> Unit))? = null,
    content: @Composable (Modifier) -> Unit,
) {
    val colors = InputContainerDefaults.colors(isError)
    val shape = InputContainerDefaults.Shape

    val baseModifier =
        modifier
            .fillMaxWidth()
            .height(InputContainerDefaults.Height)
            .clip(shape)
            .background(colors.background, shape)
            .border(1.dp, colors.border, shape)
            .padding(horizontal = InputContainerDefaults.Padding)

    val clickableModifier =
        if (enabled && onClick != null) {
            baseModifier.noRippleClickable { onClick() }
        } else {
            baseModifier
        }

    Box(
        modifier = clickableModifier,
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content(Modifier.weight(1f))
            if (trailing != null) {
                Spacer(Modifier.width(4.dp))
                trailing()
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun Preview_AppInputs() {
    var text by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AppTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = "텍스트를 입력하세요",
            isError = false,
            trailing = { AppIconButton(iconResId = R.drawable.ic_search_24) },
        )

        AppSelectField(
            text = selected,
            placeholder = "방문일을 선택하세요",
            onClick = {
                selected = if (selected == null) "11월 25일 화" else null
            },
            isError = false,
        )
    }
}
