package com.arttrip.android.presentation.my

import androidx.lifecycle.ViewModel
import com.arttrip.android.data.local.auth.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val sessionManager: SessionManager,
    ) : ViewModel() {
        fun logout() {
            sessionManager.logout()
        }
    }
