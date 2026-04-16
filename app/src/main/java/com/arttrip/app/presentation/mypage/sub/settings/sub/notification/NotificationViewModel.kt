package com.arttrip.app.presentation.mypage.sub.settings.sub.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.app.data.local.auth.SessionManager
import com.arttrip.app.presentation.mypage.sub.settings.sub.notification.contract.NotificationEffect
import com.arttrip.app.presentation.mypage.sub.settings.sub.notification.contract.NotificationIntent
import com.arttrip.app.presentation.mypage.sub.settings.sub.notification.contract.NotificationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel
    @Inject
    constructor(
        private val sessionManager: SessionManager,
    ) : ViewModel() {
        private val _state = MutableStateFlow(NotificationState())
        val state: StateFlow<NotificationState> = _state

        private val _effect = MutableSharedFlow<NotificationEffect>()
        val effect: SharedFlow<NotificationEffect> = _effect

        fun onIntent(intent: NotificationIntent) {
            when (intent) {
                NotificationIntent.BackClicked -> viewModelScope.launch { _effect.emit(NotificationEffect.NavigateBack) }
                is NotificationIntent.ExhibitionInfoToggled -> {
                    _state.update { it.copy(exhibitionInfoEnabled = intent.enabled) }
                }
                is NotificationIntent.MarketingPushToggled -> {
                    _state.update { it.copy(marketingPushEnabled = intent.enabled) }
                }
                is NotificationIntent.SavedExhibitionOpenToggled -> {
                    _state.update { it.copy(savedExhibitionOpenEnabled = intent.enabled) }
                }
                is NotificationIntent.StampIssuedToggled -> {
                    _state.update { it.copy(stampIssuedEnabled = intent.enabled) }
                }
            }
        }
    }
