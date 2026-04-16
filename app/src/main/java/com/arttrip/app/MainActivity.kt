package com.arttrip.app

import ToastController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.arttrip.app.core.navigation.app.AppNavHost
import com.arttrip.app.core.ui.component.toast.AppToastHost
import com.arttrip.app.core.ui.theme.ArtTripTheme
import com.arttrip.app.core.util.LocalToastController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        enableEdgeToEdge()
        setContent {
            ArtTripTheme {
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()
                val toastController = remember { ToastController(scope) }

                CompositionLocalProvider(LocalToastController provides toastController) {
                    Box(Modifier.fillMaxSize()) {
                        AppNavHost(navController = navController)

                        AppToastHost(hostState = toastController.hostState)
                    }
                }
            }
        }
    }
}
