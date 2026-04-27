package com.arttrip.app.data.local.fcm

import android.util.Log
import com.arttrip.app.domain.usecase.notification.RegisterFcmTokenUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArtTripMessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var registerFcmTokenUseCase: RegisterFcmTokenUseCase

    @Inject
    lateinit var fcmEventBus: FcmEventBus

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(token: String) {
        Log.d(TAG, "FCM token refreshed: $token")
        scope.launch {
            runCatching { registerFcmTokenUseCase(token).collect() }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(
            TAG,
            "FCM message received: from=${message.from}, title=${message.notification?.title}, body=${message.notification?.body}, data=${message.data}",
        )
        val title = message.notification?.title ?: return
        val body = message.notification?.body ?: ""
        scope.launch {
            fcmEventBus.emit(FcmMessage(title = title, body = body))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        private const val TAG = "ArtTripMessagingService"
    }
}
