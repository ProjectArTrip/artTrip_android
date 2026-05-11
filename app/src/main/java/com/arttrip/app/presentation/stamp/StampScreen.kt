package com.arttrip.app.presentation.stamp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arttrip.app.R
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.core.ui.theme.ArtTripTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StampScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current

    val screenHeight = with(density) { windowInfo.containerSize.height.toDp() }
    val statusBarHeight = with(density) { WindowInsets.statusBars.getTop(density).toDp() }
    val bottomNavHeight = innerPadding.calculateBottomPadding()
    val sheetHeight = screenHeight - bottomNavHeight - statusBarHeight - 224.dp

    BottomSheetScaffold(
        modifier =
            modifier
                .padding(bottom = innerPadding.calculateBottomPadding()),
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContainerColor = AppColor.Gray0,
        sheetSwipeEnabled = false,
        sheetDragHandle = {},
        sheetPeekHeight = sheetHeight,
        sheetContent = {
            StampSheetContent(sheetHeight)
        },
    ) {
        StampHeader()
    }
}

@Composable
private fun StampHeader() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.img_stamp_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Icon(
            painter = painterResource(R.drawable.ic_alert_24),
            contentDescription = "알림",
            tint = AppColor.TextWhite,
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
                    .padding(top = 12.dp, end = 24.dp),
        )

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.height(128.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_stamp_circle_bg_104),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
                Image(
                    painter = painterResource(R.drawable.ic_stamp_mascot_120),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .align(Alignment.TopCenter)
                            .blur(4.dp),
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "여행의 시작",
                style = AppTextStyle.Title01Bold,
                color = AppColor.TextWhite,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "이번달 스탬프 등급",
                style = AppTextStyle.Body02Regular,
                color = AppColor.TextWhite.copy(alpha = 0.5f),
            )
        }
    }
}

@Composable
private fun StampSheetContent(sheetHeight: Dp) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(sheetHeight)
                .background(color = AppColor.SubLightGray)
                .padding(start = 24.dp, end = 24.dp, top = 25.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "다음달 예상 등급",
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_information_20),
                contentDescription = null,
            )
            Spacer(Modifier.weight(1f))
            Text(
                "0%",
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPoint,
            )
        }
        Spacer(Modifier.height(8.dp))
        StampProgressBar()
        Spacer(Modifier.height(8.dp))
        Text(
            "스탬프 기능은 현재 준비 중입니다.",
            style = AppTextStyle.Body02Regular,
            color = AppColor.SubRed,
        )
        Spacer(Modifier.height(18.dp))
        StampMap(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StampProgressBar() {
    val progress by remember { mutableFloatStateOf(0f) } // 0f ~ 1f

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(16.dp)
                .border(1.dp, AppColor.Gray100, CircleShape)
                .padding(4.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        if (progress == 0f) {
            Box(
                modifier =
                    Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(AppColor.Gray100),
            )
        } else {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(AppColor.Primary300),
            )
        }
    }
}

@Composable
private fun StampMap(modifier: Modifier = Modifier) {
    val stampSize = 104.dp
    val pairStep = 116.dp
    // 수평(→) 대각(↘) 수평(←) 대각(↙) 반복
    val xFractions = listOf(0.15f, 0.5f, 0.82f, 0.5f)
    val density = LocalDensity.current

    var containerWidth by remember { mutableFloatStateOf(0f) }
    var containerHeight by remember { mutableFloatStateOf(0f) }

    val stampSizePx = with(density) { stampSize.toPx() }
    val stepPx = with(density) { pairStep.toPx() }

    val positions =
        remember(containerWidth, containerHeight, stepPx, stampSizePx) {
            if (containerHeight == 0f) return@remember emptyList()
            val pairCount = ((containerHeight / stepPx) + 1).toInt().coerceAtLeast(1)
            (0 until pairCount * 2).map { i ->
                Offset(
                    x = containerWidth * xFractions[i % 4],
                    y = stampSizePx / 2 + (i / 2) * stepPx, // 2개씩 같은 y 레벨
                )
            }
        }

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clipToBounds()
                .onSizeChanged { size ->
                    containerWidth = size.width.toFloat()
                    containerHeight = size.height.toFloat()
                }.drawBehind {
                    if (positions.size > 1) {
                        drawPath(
                            path =
                                Path().apply {
                                    moveTo(positions[0].x, positions[0].y)
                                    positions.drop(1).forEach { lineTo(it.x, it.y) }
                                },
                            color = AppColor.Primary200,
                            style =
                                Stroke(
                                    width = 2.dp.toPx(),
                                    cap = StrokeCap.Round,
                                    // Round cap이 양끝 1dp씩 확장되므로 도트 8→6dp, 간격 4→6dp로 보정
                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 6.dp.toPx())),
                                ),
                        )
                    }
                },
    ) {
        positions.forEach { center ->
            Image(
                painter = painterResource(R.drawable.ic_stamp_empty_104),
                contentDescription = null,
                modifier =
                    Modifier
                        .offset(
                            x = with(density) { center.x.toDp() } - stampSize / 2,
                            y = with(density) { center.y.toDp() } - stampSize / 2,
                        ).size(stampSize),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StampMapPreview() {
    ArtTripTheme {
        StampMap(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(500.dp),
        )
    }
}
