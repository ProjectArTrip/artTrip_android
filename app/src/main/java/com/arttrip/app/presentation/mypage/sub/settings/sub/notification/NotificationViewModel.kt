package com.arttrip.app.presentation.mypage.sub.settings.sub.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.app.core.ui.UiMessage
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.usecase.notification.GetPushEnabledUseCase
import com.arttrip.app.domain.usecase.notification.UpdatePushEnabledUseCase
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
        val getPushEnabledUseCase: GetPushEnabledUseCase,
        val updatePushEnabledUseCase: UpdatePushEnabledUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(NotificationState())
        val state: StateFlow<NotificationState> = _state

        private val _effect = MutableSharedFlow<NotificationEffect>()
        val effect: SharedFlow<NotificationEffect> = _effect

        init {
            getPushEnabled()
        }

        fun onIntent(intent: NotificationIntent) {
            when (intent) {
                NotificationIntent.BackClicked -> viewModelScope.launch { _effect.emit(NotificationEffect.NavigateBack) }
                is NotificationIntent.ExhibitionInfoToggled -> {
                    _state.update { it.copy(exhibitionInfoEnabled = intent.enabled) }
                }
                is NotificationIntent.NoticePushToggled -> {
                    _state.update { it.copy(noticePushEnabled = intent.enabled) }
                    updatePushEnabled(intent.enabled)
                }
            }
        }

        private fun getPushEnabled() {
            viewModelScope.launch {
                getPushEnabledUseCase().collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {}

                        is ApiResult.Success -> {
                            _state.update { it.copy(noticePushEnabled = result.data) }
                        }
                        is ApiResult.Error -> {
                        }
                    }
                }
            }
        }

        private fun updatePushEnabled(isEnabled: Boolean) {
            viewModelScope.launch {
                updatePushEnabledUseCase(isEnabled).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {}

                        is ApiResult.Success -> {
                        }
                        is ApiResult.Error -> {
                            _effect.emit(NotificationEffect.ShowToast(UiMessage.ERROR_RETRY_LATER))
                            _state.update { it.copy(noticePushEnabled = !isEnabled) }
                        }
                    }
                }
            }
        }
    }
