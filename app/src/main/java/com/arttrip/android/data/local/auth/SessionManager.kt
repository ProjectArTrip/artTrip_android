package com.arttrip.android.data.local.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager
    @Inject
    constructor(
        private val tokenManager: TokenManager,
    ) {
        private val _logoutSignal = MutableStateFlow(0)
        val logoutSignal: StateFlow<Int> = _logoutSignal

        fun logout() {
            tokenManager.clear()
            _logoutSignal.value += 1
        }

        fun consumeLogoutSignal() {
            _logoutSignal.value = 0
        }
    }
