package com.arttrip.app.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.app.data.local.auth.SessionManager
import com.arttrip.app.data.local.fcm.FcmEventBus
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.usecase.notification.GetHasUnreadNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val sessionManager: SessionManager,
        private val fcmEventBus: FcmEventBus,
        private val getHasUnreadNotificationUseCase: GetHasUnreadNotificationUseCase,
    ) : ViewModel() {
        val logoutSignal = sessionManager.logoutSignal
        val pendingDeepLink = fcmEventBus.pendingDeepLink

        private val _hasUnread = MutableStateFlow(false)
        val hasUnread: StateFlow<Boolean> = _hasUnread

        init {
            refreshHasUnread()
            observeFcmMessages()
        }

        private fun observeFcmMessages() {
            viewModelScope.launch {
                fcmEventBus.messages.collect {
                    _hasUnread.value = true
                }
            }
        }

        fun refreshHasUnread() {
            viewModelScope.launch {
                getHasUnreadNotificationUseCase().collectLatest { result ->
                    if (result is ApiResult.Success) {
                        _hasUnread.value = result.data
                    }
                }
            }
        }

        fun consumeLogoutSignal() {
            sessionManager.consumeLogoutSignal()
        }

        fun consumeDeepLink() {
            fcmEventBus.consumeDeepLink()
        }
    }
