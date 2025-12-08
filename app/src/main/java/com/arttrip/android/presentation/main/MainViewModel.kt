package com.arttrip.android.presentation.main

import androidx.lifecycle.ViewModel
import com.arttrip.android.data.local.auth.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val sessionManager: SessionManager,
    ) : ViewModel() {
        val logoutEvents = sessionManager.logoutEvents
    }
