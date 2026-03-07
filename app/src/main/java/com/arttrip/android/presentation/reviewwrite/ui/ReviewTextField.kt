package com.arttrip.android.presentation.reviewwrite.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.ui.theme.ArtTripTheme
import org.w3c.dom.Text

@Composable
fun ReviewTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    maxChars: Int,
    placeholder: String = "리뷰 작성해주세요.",
    showError: Boolean = false,
    enabled: Boolean = true,
) {
    val countStr = value.length.toString()

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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (showError) {
                    Text(
                        "최소 20자 이상 입력해주세요.",
                        style = AppTextStyle.Body03Regular,
                        color = AppColor.SubRed,
                    )
                } else {
                    Spacer(Modifier)
                }
                Text(
                    text =
                        buildAnnotatedString {
                            withStyle(
                                style =
                                    AppTextStyle.Body02Bold
                                        .toSpanStyle()
                                        .copy(color = AppColor.TextSecondary),
                            ) {
                                append(countStr)
                            }

                            withStyle(
                                style =
                                    AppTextStyle.Body02Regular
                                        .toSpanStyle()
                                        .copy(color = AppColor.TextTertiary),
                            ) {
                                append("/$maxChars")
                            }
                        },
                )
            }
        }
    }
}

@Preview(name = "ReviewTextField - Error", showBackground = true)
@Composable
private fun PreviewReviewTextField_Filled() {
    ArtTripTheme {
        ReviewTextField(
            value = "전시 구성도..",
            onValueChange = {},
            showError = true,
            modifier = Modifier.padding(24.dp),
            maxChars = 300,
        )
    }
}
