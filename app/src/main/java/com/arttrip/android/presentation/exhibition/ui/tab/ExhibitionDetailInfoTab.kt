package com.arttrip.android.presentation.exhibition.ui.tab

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.domain.model.exhibit.ExhibitionDetailModel

@Composable
fun ExhibitionDetailInfoTab(detail: ExhibitionDetailModel) {
    val address = detail.hallAddress?.trim().orEmpty()
    val desc = detail.description?.trim().orEmpty()

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(AppColor.SubLightGray)
                    .padding(horizontal = 16.dp, vertical = 20.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                IconTextContentRow(
                    iconResId = R.drawable.ic_location_20,
                    title = "주소",
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                    ) {
                        InfoValueText(
                            modifier = Modifier.weight(1f, fill = false),
                            value = address,
                        )
                        if (address.isNotBlank()) {
                            Spacer(Modifier.width(12.dp))
                            CopyActionText(textToCopy = address)
                        }
                    }
                }

                IconTextContentRow(
                    iconResId = R.drawable.ic_duration_20,
                    title = "운영시간",
                ) {
                    InfoValueText(value = detail.hallOpeningHours)
//                    Spacer(Modifier.height(4.dp))
//                    Text(
//                        "휴관일: 매주 월요일",
//                        style = AppTextStyle.Body02Regular,
//                        color = AppColor.SubRed,
//                    )
                }

                IconTextContentRow(
                    iconResId = R.drawable.ic_contact_number_20,
                    title = "전화번호",
                ) {
                    InfoValueText(value = detail.hallPhone)
                }
            }
        }
        if (desc.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(AppColor.SubLightGray)
                        .padding(horizontal = 16.dp, vertical = 20.dp),
            ) {
                Text(
                    detail.description.orEmpty(),
                    style = AppTextStyle.Body01Regular,
                    color = AppColor.TextPrimary,
                )
            }
        }
    }
}

@Composable
private fun IconTextContentRow(
    @DrawableRes iconResId: Int,
    title: String,
    modifier: Modifier = Modifier,
    leftWidth: Dp = 68.dp,
    gapAfterLeft: Dp = 8.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    SubcomposeLayout(modifier = modifier.fillMaxWidth()) { constraints ->
        val leftW = leftWidth.roundToPx()
        val gapW = gapAfterLeft.roundToPx()

        val leftPlaceable =
            subcompose("left") {
                Row(
                    modifier = Modifier.width(leftWidth),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = title,
                        style = AppTextStyle.Body02Bold,
                        color = AppColor.TextPrimary,
                    )
                }
            }.first().measure(
                constraints.copy(
                    minWidth = leftW,
                    maxWidth = leftW,
                ),
            )

        val contentMaxW = (constraints.maxWidth - leftW - gapW).coerceAtLeast(0)
        val contentPlaceable =
            subcompose("content") {
                Column(Modifier.fillMaxWidth()) { content() }
            }.first().measure(
                constraints.copy(
                    minWidth = 0,
                    maxWidth = contentMaxW,
                ),
            )

        val height = maxOf(leftPlaceable.height, contentPlaceable.height)

        val leftY = 0
        val contentY =
            if (contentPlaceable.height <= leftPlaceable.height) {
                (leftPlaceable.height - contentPlaceable.height) / 2
            } else {
                0
            }

        layout(constraints.maxWidth, height) {
            leftPlaceable.placeRelative(0, leftY)
            contentPlaceable.placeRelative(leftW + gapW, contentY)
        }
    }
}

@Composable
private fun InfoValueText(
    modifier: Modifier = Modifier,
    value: String?,
    emptyText: String = "정보 없음",
) {
    val v = value?.trim().orEmpty()
    val isEmpty = v.isBlank()

    Text(
        text = if (isEmpty) emptyText else v,
        style = AppTextStyle.Body02Regular,
        color = if (isEmpty) AppColor.TextTertiary else AppColor.TextPrimary,
        modifier = modifier,
    )
}

@Composable
private fun CopyActionText(
    textToCopy: String,
    modifier: Modifier = Modifier,
) {
    val clipboard = LocalClipboardManager.current

    Text(
        text = "복사",
        style = AppTextStyle.Body02Regular,
        color = AppColor.Primary300,
        modifier =
            modifier.noRippleClickable {
                clipboard.setText(AnnotatedString(textToCopy))
                // 필요하면 토스트/스낵바는 상위에서 effect로 처리
            },
    )
}
