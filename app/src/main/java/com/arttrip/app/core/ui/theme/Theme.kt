package com.arttrip.app.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme =
    lightColorScheme(
        primary = AppColor.Primary300,
        onPrimary = AppColor.TextWhite,
        background = AppColor.Gray0,
        onBackground = AppColor.TextPrimary,
        surface = AppColor.Gray0,
        onSurface = AppColor.TextPrimary,
        error = AppColor.SubRed,
    )

@Composable
fun ArtTripTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
