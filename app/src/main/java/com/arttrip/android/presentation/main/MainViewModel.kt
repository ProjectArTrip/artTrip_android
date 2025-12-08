package com.arttrip.android.presentation.main

import androidx.lifecycle.ViewModel
import com.arttrip.android.data.local.auth.SessionManager
import com.arttrip.android.presentation.main.contract.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val sessionManager: SessionManager,
    ) : ViewModel() {
        private val _state = MutableStateFlow(MainState())
        val state: StateFlow<MainState> = _state.asStateFlow()

        val logoutEvents = sessionManager.logoutEvents
    }
