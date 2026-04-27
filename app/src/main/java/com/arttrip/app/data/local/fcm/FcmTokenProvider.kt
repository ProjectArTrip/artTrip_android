package com.arttrip.app.data.local.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class FcmTokenProvider
    @Inject
    constructor() {
        suspend fun getToken(): String =
            suspendCoroutine { cont ->
                FirebaseMessaging
                    .getInstance()
                    .token
                    .addOnSuccessListener { token ->
                        cont.resume(token)
                    }.addOnFailureListener { e ->
                        Log.e(TAG, "FCM token fetch failed", e)
                        cont.resumeWithException(e)
                    }
            }

        companion object {
            private const val TAG = "FcmTokenProvider"
        }
    }
