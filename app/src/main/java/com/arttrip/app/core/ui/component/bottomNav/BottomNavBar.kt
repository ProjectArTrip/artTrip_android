package com.arttrip.app.core.ui.component.bottomNav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.arttrip.app.core.navigation.main.BottomNavItem
import com.arttrip.app.core.navigation.main.bottomNavItems
import com.arttrip.app.core.ui.theme.AppColor

@Composable
fun AppBottomNavBarWithInset(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem>,
    selectedRoute: String?,
    onItemSelected: (BottomNavItem) -> Unit,
) {
    val bottomInset =
        WindowInsets.navigationBars
            .asPaddingValues()
            .calculateBottomPadding()

    Column(modifier = modifier.fillMaxWidth()) {
        AppBottomNavBar(
            items = items,
            selectedRoute = selectedRoute,
            onItemSelected = onItemSelected,
        )

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(bottomInset)
                    .background(AppColor.Gray0),
        )
    }
}

/**
 * 피그마상 시스템바 영역인 24px뺀 바텀네비
 * */
@Composable
fun AppBottomNavBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem>,
    selectedRoute: String?,
    onItemSelected: (BottomNavItem) -> Unit,
) {
    val stampIndex = 2
    // val barHeight = 86.dp
    val totalHeight = 62.dp
    val contentBottomPadding = 6.dp
    val contentTopPadding = 8.dp
    val horizontalPadding = 16.dp
    val stampSlotWidth = 46.dp
    val stampSlotHeight = 48.dp

    val stampItem = items[stampIndex]
    val stampSelected = selectedRoute == stampItem.route

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(totalHeight),
        contentAlignment = Alignment.BottomCenter,
    ) {
        // --- main bar surface ---
        Surface(
            color = AppColor.Gray0,
            shape = BottomNavOuterShape,
            tonalElevation = 0.dp,
            shadowElevation = 5.dp,
            modifier =
                Modifier
                    .fillMaxWidth(),
// .height(barHeight),
        ) {
            Row(
                modifier =
                    Modifier
                        .wrapContentHeight()
                        .padding(
                            start = horizontalPadding,
                            end = horizontalPadding,
                            top = contentTopPadding,
                            bottom = contentBottomPadding,
                        ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items.forEachIndexed { index, item ->
                    if (index == stampIndex) {
                        // 중앙 스탬프 자리 비워두기
                        Spacer(
                            modifier =
                                Modifier
                                    .width(stampSlotWidth)
                                    .height(stampSlotHeight),
                        )
                    } else {
                        val selected = item.route == selectedRoute
                        AppBottomNavItem(
                            item = item,
                            selected = selected,
                            onClick = { onItemSelected(item) },
                        )
                    }
                }
            }
        }

        // --- floating stamp button ---
        AppBottomNavCenterItem(
            item = stampItem,
            selected = stampSelected,
            onClick = { onItemSelected(stampItem) },
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = -contentBottomPadding),
        )
    }
}

private object BottomNavOuterShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline =
        with(density) {
            val w = size.width
            val h = size.height

            val notchWidth = 80.dp.toPx()
            val notchDepth = 30.dp.toPx()
            val shoulderFactor = 0.27f
            val centerLift = 2f // centerX, -2f 에서 쓰던 중앙 높이(px)

            val outerRadius = 32.dp.toPx()
            val bottomOffset = 16.dp.toPx()
            // ============================

            val centerX = w / 2f
            val notchLeft = centerX - notchWidth / 2f
            val notchRight = centerX + notchWidth / 2f

            // 1) 바텀바(노치 포함) 외곽 Path
            val barPath =
                Path().apply {
                    moveTo(0f, 0f)

                    lineTo(notchLeft, 0f)

                    cubicTo(
                        notchLeft + notchWidth * shoulderFactor,
                        0f,
                        notchLeft + notchWidth * shoulderFactor,
                        -notchDepth,
                        centerX,
                        -centerLift,
                    )
                    cubicTo(
                        notchRight - notchWidth * shoulderFactor,
                        -notchDepth,
                        notchRight - notchWidth * shoulderFactor,
                        0f,
                        notchRight,
                        0f,
                    )

                    lineTo(w, 0f)
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()
                }

            // 2) 가운데 원(튀어나온 부분) Path
            val cx = centerX
            val cy = h - bottomOffset - outerRadius

            val circlePath =
                Path().apply {
                    addOval(
                        Rect(
                            left = cx - outerRadius,
                            top = cy - outerRadius,
                            right = cx + outerRadius,
                            bottom = cy + outerRadius,
                        ),
                    )
                }

            // 3) 두 외곽을 합쳐서 “전체 실루엣” 만들기
            val outerPath =
                Path.combine(
                    operation = PathOperation.Union,
                    path1 = barPath,
                    path2 = circlePath,
                )

            Outline.Generic(outerPath)
        }
}

@Preview(
    name = "BottomNavBar Only",
    showBackground = true,
    widthDp = 360,
    heightDp = 200,
)
@Composable
private fun Preview_AppBottomNavBar_Interactive() {
    var selectedRoute by remember { mutableStateOf(bottomNavItems.first().route) }

    AppBottomNavBar(
        items = bottomNavItems,
        selectedRoute = selectedRoute,
        onItemSelected = { item -> selectedRoute = item.route },
    )
}
