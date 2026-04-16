package com.arttrip.app.presentation.main

import androidx.lifecycle.ViewModel
import com.arttrip.app.data.local.auth.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val sessionManager: SessionManager,
    ) : ViewModel() {
        val logoutSignal = sessionManager.logoutSignal

        fun consumeLogoutSignal() {
            sessionManager.consumeLogoutSignal()
        }
    }
