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
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle

/**
 * ### Figma: Tag
 *
 * - Ongoing → "진행중", Deadline → "마감임박" (Figma 매핑)
 */
@Composable
fun AppTag(
    modifier: Modifier = Modifier,
    type: AppTagType,
) {
    val colors = AppTagDefaults.colors(type)
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
                .background(color = colors.backgroundColor, shape = shape)
                .run {
                    if (type == AppTagType.Deadline) {
                        deadlineBorder(roundDp, colors.borderColor)
                    } else {
                        this
                    }
                }.padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = type.label,
            style = textStyle,
            color = colors.textColor,
        )
    }
}

/**
 * ### Figma: Tag(L)
 *
 * - Ongoing → "진행중", Deadline → "마감임박" (Figma 매핑)
 */
@Composable
fun AppTagL(
    modifier: Modifier = Modifier,
    type: AppTagType,
) {
    val colors = AppTagDefaults.colors(type)
    val textStyle = AppTextStyle.Body01Bold
    val roundDp = 6.dp

    val shape =
        RoundedCornerShape(size = roundDp)

    Box(
        modifier =
            modifier
                .clip(shape)
                .background(color = colors.backgroundColor, shape = shape)
                .then(
                    if (type == AppTagType.Deadline) Modifier.border(1.dp, colors.borderColor, shape) else Modifier,
                ).padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = type.label,
            style = textStyle,
            color = colors.textColor,
        )
    }
}

/** Figma Tag 타입 매핑. */
enum class AppTagType(
    val label: String,
) {
    /** 진행중 */
    Ongoing("진행중"),

    /** 마감임박 */
    Deadline("마감임박"),
}

@Stable
data class AppTagColors(
    val textColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
)

object AppTagDefaults {
    fun colors(type: AppTagType): AppTagColors =
        when (type) {
            AppTagType.Ongoing ->
                AppTagColors(
                    textColor = AppColor.TextPrimary,
                    backgroundColor = AppColor.SubLime,
                    borderColor = Color.Transparent,
                )

            AppTagType.Deadline ->
                AppTagColors(
                    textColor = AppColor.TextPrimary,
                    backgroundColor = AppColor.Gray0,
                    borderColor = AppColor.Gray50,
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
        AppTag(type = AppTagType.Deadline)
        AppTag(type = AppTagType.Ongoing)
    }
}

@Preview
@Composable
fun SampleAppTagL() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        AppTagL(type = AppTagType.Deadline)
        AppTagL(type = AppTagType.Ongoing)
    }
}
