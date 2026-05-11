package com.arttrip.app.presentation.main

import androidx.lifecycle.ViewModel
import com.arttrip.app.data.local.auth.SessionManager
import com.arttrip.app.data.local.fcm.FcmEventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val sessionManager: SessionManager,
        private val fcmEventBus: FcmEventBus,
    ) : ViewModel() {
        val logoutSignal = sessionManager.logoutSignal
        val pendingDeepLink = fcmEventBus.pendingDeepLink

        fun consumeLogoutSignal() {
            sessionManager.consumeLogoutSignal()
        }

        fun consumeDeepLink() {
            fcmEventBus.consumeDeepLink()
        }
    }
