package com.arttrip.android.core.ui.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.model.enum.exhibit.ExhibitStatus
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle

/**
 * ### Figma: Tag
 *
 * - ONGOING → "진행중"
 * - ENDING_SOON → "마감임박"
 * - UPCOMING → "전시예정"
 * - FINISHED → 표시하지 않음
 */
@Composable
fun AppTag(
    modifier: Modifier = Modifier,
    status: ExhibitStatus,
) {
    if (status == ExhibitStatus.FINISHED) return

    val ui = AppTagDefaults.ui(status)
    val textStyle = AppTextStyle.Body02Bold
    val roundDp = 8.dp

    val shape =
        RoundedCornerShape(
            topStart = roundDp,
            topEnd = 0.dp,
            bottomEnd = roundDp,
            bottomStart = 0.dp,
        )

    Box(
        modifier =
            modifier
                .clip(shape)
                .background(color = ui.colors.backgroundColor, shape = shape)
                .run {
                    if (ui.hasBorder) deadlineBorder(roundDp, ui.colors.borderColor) else this
                }.padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = ui.label,
            style = textStyle,
            color = ui.colors.textColor,
        )
    }
}

/**
 * ### Figma: Tag(L)
 *
 * - ONGOING → "진행중"
 * - ENDING_SOON → "마감임박"
 * - UPCOMING → "전시예정"
 * - FINISHED → 표시하지 않음
 */
@Composable
fun AppTagL(
    modifier: Modifier = Modifier,
    status: ExhibitStatus,
) {
    if (status == ExhibitStatus.FINISHED) return

    val ui = AppTagDefaults.ui(status)
    val textStyle = AppTextStyle.Body01Bold
    val roundDp = 6.dp

    val shape = RoundedCornerShape(size = roundDp)

    Box(
        modifier =
            modifier
                .clip(shape)
                .background(color = ui.colors.backgroundColor, shape = shape)
                .then(
                    if (ui.hasBorder) Modifier.border(1.dp, ui.colors.borderColor, shape) else Modifier,
                ).padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = ui.label,
            style = textStyle,
            color = ui.colors.textColor,
        )
    }
}

@Stable
private data class AppTagColors(
    val textColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
)

@Stable
private data class AppTagUi(
    val label: String,
    val colors: AppTagColors,
    val hasBorder: Boolean,
)

private object AppTagDefaults {
    /**
     * 전시 상태 → Tag UI 매핑
     * FINISHED는 렌더링하지 않으므로 이 함수가 호출되지 않도록 상위에서 return 처리.
     */
    fun ui(status: ExhibitStatus): AppTagUi =
        when (status) {
            ExhibitStatus.ONGOING ->
                AppTagUi(
                    label = "진행중",
                    hasBorder = false,
                    colors =
                        AppTagColors(
                            textColor = AppColor.TextPrimary,
                            backgroundColor = AppColor.SubLime,
                            borderColor = Color.Transparent,
                        ),
                )

            ExhibitStatus.ENDING_SOON ->
                AppTagUi(
                    label = "마감임박",
                    hasBorder = true,
                    colors =
                        AppTagColors(
                            textColor = AppColor.TextPrimary,
                            backgroundColor = AppColor.Gray0,
                            borderColor = AppColor.Gray50,
                        ),
                )

            ExhibitStatus.UPCOMING ->
                AppTagUi(
                    label = "전시예정",
                    hasBorder = true,
                    colors =
                        AppTagColors(
                            textColor = AppColor.TextPoint,
                            backgroundColor = AppColor.SubLightGray,
                            borderColor = AppColor.Gray100,
                        ),
                )

            ExhibitStatus.FINISHED ->
                AppTagUi(
                    label = "",
                    hasBorder = false,
                    colors =
                        AppTagColors(
                            textColor = Color.Transparent,
                            backgroundColor = Color.Transparent,
                            borderColor = Color.Transparent,
                        ),
                )
        }
}

private fun Modifier.deadlineBorder(
    roundDp: Dp,
    borderColor: Color,
) = drawBehind {
    val stroke = 1.dp.toPx()
    val half = stroke / 2f
    val r = roundDp.toPx()

    val w = size.width
    val h = size.height
    val iw = w - stroke
    val ih = h - stroke

    val path =
        Path().apply {
            moveTo(half, ih + half)
            lineTo(iw - r + half, ih + half)
            arcTo(
                rect =
                    Rect(
                        left = iw - 2 * r + half,
                        top = ih - 2 * r + half,
                        right = iw + half,
                        bottom = ih + half,
                    ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false,
            )
            lineTo(iw + half, half)
        }

    drawPath(
        path = path,
        color = borderColor,
        style = Stroke(width = stroke, cap = StrokeCap.Butt, join = StrokeJoin.Round),
    )
}

@Preview
@Composable
fun SampleAppTag() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        AppTag(status = ExhibitStatus.ENDING_SOON)
        AppTag(status = ExhibitStatus.ONGOING)
        AppTag(status = ExhibitStatus.UPCOMING)
        // FINISHED는 표시 안 됨 (아무것도 렌더링되지 않음)
        AppTag(status = ExhibitStatus.FINISHED)
    }
}

@Preview
@Composable
fun SampleAppTagL() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AppTagL(status = ExhibitStatus.ENDING_SOON)
        AppTagL(status = ExhibitStatus.ONGOING)
        AppTagL(status = ExhibitStatus.UPCOMING)
        // FINISHED는 표시 안 됨
        AppTagL(status = ExhibitStatus.FINISHED)
    }
}
