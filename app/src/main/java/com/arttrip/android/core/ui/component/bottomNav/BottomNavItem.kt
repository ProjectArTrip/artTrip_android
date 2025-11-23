package com.arttrip.android.core.ui.component.bottomNav

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.navigation.BottomNavItem
import com.arttrip.android.core.navigation.bottomNavItems
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.ui.theme.ArtTripTheme
import com.arttrip.android.core.util.noRippleClickable

/** 일반 네비 탭 아이템 */
@Composable
fun AppBottomNavItem(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val visuals = rememberNavItemVisuals(selected)

    Column(
        modifier =
            modifier
                .noRippleClickable { onClick() }
                .width(46.dp)
                .height(48.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = item.iconRes),
            contentDescription = item.label,
            tint = visuals.contentColor,
        )
        Text(
            text = item.label,
            style = visuals.textStyle,
            color = visuals.contentColor,
        )
    }
}

/** 가운데 스탬프(FAB) 아이템 */
@Composable
fun AppBottomNavCenterItem(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: Dp = 48.dp,
    labelGap: Dp = 4.dp,
) {
    val visuals = rememberNavItemVisuals(selected)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(labelGap),
        modifier =
            modifier.noRippleClickable { onClick() },
    ) {
        Surface(
            shape = CircleShape,
            color = Color.Transparent,
            modifier =
                Modifier
                    .size(buttonSize)
                    .linearGradientCircleBackground(),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Icon(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = item.label,
                    tint = AppColor.Gray0, // 스탬프는 흰색 고정
                )
            }
        }

        Text(
            text = item.label,
            style = visuals.textStyle,
            color = visuals.contentColor,
        )
    }
}

private fun Modifier.linearGradientCircleBackground(): Modifier =
    this
        .graphicsLayer {
            shape = CircleShape
            clip = true
        }.drawWithCache {
            fun axToFx(ax: Float) = (ax + 1f) / 2f

            fun ayToFy(ay: Float) = (ay + 1f) / 2f

            val start =
                Offset(
                    x = size.width * axToFx(0.13f),
                    y = size.height * ayToFy(-0.00f),
                )
            val end =
                Offset(
                    x = size.width * axToFx(0.68f),
                    y = size.height * ayToFy(1.00f),
                )

            val brush =
                Brush.linearGradient(
                    colors = listOf(Color(0xFFAA96FF), Color(0xFF7859FF)),
                    start = start,
                    end = end,
                )

            onDrawBehind { drawRect(brush) }
        }

@Immutable
private data class NavItemVisuals(
    val contentColor: Color,
    val textStyle: androidx.compose.ui.text.TextStyle,
)

@Composable
private fun rememberNavItemVisuals(selected: Boolean): NavItemVisuals {
    val contentColor = if (selected) AppColor.Primary300 else AppColor.Gray900
    val textStyle = if (selected) AppTextStyle.Body02Bold else AppTextStyle.Body02Regular
    return NavItemVisuals(contentColor, textStyle)
}

@Preview(showBackground = true)
@Composable
private fun Preview_BottomNavItems_NormalAndCenter() {
    ArtTripTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppBottomNavItem(
                item = bottomNavItems[3],
                selected = true,
                onClick = {},
            )

            AppBottomNavCenterItem(
                item = bottomNavItems[2],
                selected = false,
                onClick = {},
            )
        }
    }
}
