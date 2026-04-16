package com.arttrip.app.core.util

import ToastController
import androidx.compose.runtime.staticCompositionLocalOf

val LocalToastController =
    staticCompositionLocalOf<ToastController> {
        error("ToastController not provided")
    }
