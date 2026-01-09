package com.arttrip.android.presentation.reviewwrite.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.ui.theme.Pretendard

@Composable
fun ReviewTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxChars: Int,
    placeholder: String = "리뷰 작성해주세요.",
    enabled: Boolean = true,
) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier =
            modifier
                .clip(shape)
                .border(width = 1.dp, color = AppColor.Gray100, shape = shape),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .padding(PaddingValues(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)),
            horizontalAlignment = Alignment.End,
        ) {
            BasicTextField(
                value = value,
                onValueChange = { new ->
                    if (!enabled) return@BasicTextField
                    if (new.length <= maxChars) {
                        onValueChange(new)
                    } else {
                        onValueChange(new.take(maxChars))
                    }
                },
                enabled = enabled,
                textStyle = AppTextStyle.Body01Regular,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                singleLine = false,
                maxLines = Int.MAX_VALUE,
                decorationBox = { innerTextField ->
                    if (value.isBlank()) {
                        Text(
                            text = placeholder,
                            style = AppTextStyle.Body01Regular,
                            color = AppColor.TextTertiary,
                        )
                    }
                    innerTextField()
                },
            )
            Text(
                text = "${value.length}/$maxChars",
                style =
                    TextStyle(
                        fontSize = 12.sp,
                        fontFamily = Pretendard,
                        fontWeight = FontWeight(700),
                    ),
            )
        }
    }
}
