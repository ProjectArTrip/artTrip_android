package com.arttrip.android.presentation.mypage.sub.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.core.model.config.AppLinks
import com.arttrip.android.data.local.auth.SessionManager
import com.arttrip.android.presentation.mypage.sub.settings.contract.SettingsEffect
import com.arttrip.android.presentation.mypage.sub.settings.contract.SettingsIntent
import com.arttrip.android.presentation.mypage.sub.settings.contract.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val sessionManager: SessionManager,
    ) : ViewModel() {
        private val _state = MutableStateFlow(SettingsState())
        val state: StateFlow<SettingsState> = _state

        private val _effect = MutableSharedFlow<SettingsEffect>()
        val effect: SharedFlow<SettingsEffect> = _effect

        fun onIntent(intent: SettingsIntent) {
            when (intent) {
                SettingsIntent.BackClicked -> viewModelScope.launch { _effect.emit(SettingsEffect.NavigateBack) }

                SettingsIntent.DeleteAccountClick -> {
                    _state.update { it.copy(isDeleteAccountDialogVisible = true) }
                }
                SettingsIntent.NoticeClick -> viewModelScope.launch { _effect.emit(SettingsEffect.NavigateToNotice) }
                SettingsIntent.NotificationClick ->
                    viewModelScope.launch {
                        _effect.emit(
                            SettingsEffect.NavigateToNotification,
                        )
                    }
                SettingsIntent.PrivacyPolicyClick -> {
                    viewModelScope.launch {
                        _effect.emit(
                            SettingsEffect.OpenWeb(AppLinks.PRIVACY_POLICY),
                        )
                    }
                }
                SettingsIntent.TermsOfServiceClick -> {
                    viewModelScope.launch {
                        _effect.emit(
                            SettingsEffect.OpenWeb(AppLinks.TERMS_OF_SERVICE),
                        )
                    }
                }
                SettingsIntent.DeleteAccountConfirmClick -> {
                    _state.update { it.copy(isDeleteAccountDialogVisible = false) }

                    // TODO 탈퇴하기
                }
                SettingsIntent.DeleteAccountDialogDismissed -> {
                    _state.update { it.copy(isDeleteAccountDialogVisible = false) }
                }
            }
        }
    }
