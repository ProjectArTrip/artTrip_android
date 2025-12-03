package com.arttrip.android.data.local.auth

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager
    @Inject
    constructor(
        private val tokenManager: TokenManager,
    ) {
        private val _logoutEvents = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val logoutEvents: SharedFlow<Unit> = _logoutEvents

        fun logout() {
            tokenManager.clear()
            _logoutEvents.tryEmit(Unit)
        }
    }
