package com.arttrip.app

import ToastController
import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.arttrip.app.core.model.enums.notification.Action
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

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleFcmIntent(intent)
    }

    private fun handleFcmIntent(intent: Intent) {
        val actionStr = intent.getStringExtra("action") ?: return
        val action = Action.entries.find { it.name == actionStr }
        if (action == null) {
            Log.w(TAG, "Unknown FCM action: $actionStr")
            return
        }
        val referenceId = intent.getStringExtra("referenceId")?.toIntOrNull()
        when (action) {
            Action.MOVE_NOTICE_DETAIL -> fcmEventBus.emitDeepLink(action, referenceId)
            else -> Unit
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
                                val action = fcmMessage?.action
                                if (action != null) {
                                    fcmEventBus.emitDeepLink(action, fcmMessage?.referenceId)
                                } else {
                                    Log.w(TAG, "FCM banner clicked but action is null or unknown")
                                    // TODO: 알림화면이동
                                }
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
