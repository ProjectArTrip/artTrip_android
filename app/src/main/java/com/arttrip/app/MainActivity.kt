package com.arttrip.app

import ToastController
import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.arttrip.app.core.navigation.app.AppNavHost
import com.arttrip.app.core.ui.component.toast.AppNotificationHost
import com.arttrip.app.core.ui.component.toast.AppToastHost
import com.arttrip.app.core.ui.theme.ArtTripTheme
import com.arttrip.app.core.util.LocalToastController
import com.arttrip.app.data.local.fcm.FcmEventBus
import com.arttrip.app.data.local.fcm.FcmMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var fcmEventBus: FcmEventBus

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleFcmIntent(intent)
    }

    private fun handleFcmIntent(intent: Intent) {
        val action = intent.getStringExtra("action") ?: return
        if (action == "MOVE_EXHIBIT_DETAIL") {
            val exhibitId = intent.getStringExtra("referenceId")?.toIntOrNull() ?: return
            fcmEventBus.emitDeepLink(exhibitId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleFcmIntent(intent)
        installSplashScreen()

        enableEdgeToEdge()
        setContent {
            ArtTripTheme {
                val notificationPermissionLauncher =
                    rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission(),
                    ) {}
                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }

                var fcmMessage by remember { mutableStateOf<FcmMessage?>(null) }
                LaunchedEffect(Unit) {
                    fcmEventBus.messages.collect { fcmMessage = it }
                }

                val navController = rememberNavController()
                val scope = rememberCoroutineScope()
                val toastController = remember { ToastController(scope) }

                CompositionLocalProvider(LocalToastController provides toastController) {
                    Box(Modifier.fillMaxSize()) {
                        AppNavHost(navController = navController)

                        AppNotificationHost(
                            message = fcmMessage,
                            onDismiss = { fcmMessage = null },
                            onClick = {
                                fcmMessage?.exhibitId?.let { fcmEventBus.emitDeepLink(it) }
                                fcmMessage = null
                            },
                        )

                        AppToastHost(hostState = toastController.hostState)
                    }
                }
            }
        }
    }
}
