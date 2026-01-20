package com.arttrip.android.core.util

import ToastController
import androidx.compose.runtime.staticCompositionLocalOf

val LocalToastController =
    staticCompositionLocalOf<ToastController> {
        error("ToastController not provided")
    }
