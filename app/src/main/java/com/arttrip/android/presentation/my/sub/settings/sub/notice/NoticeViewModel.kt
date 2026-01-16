package com.arttrip.android.presentation.my.sub.settings.sub.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.data.local.auth.SessionManager
import com.arttrip.android.presentation.my.sub.settings.sub.notice.contract.NoticeEffect
import com.arttrip.android.presentation.my.sub.settings.sub.notice.contract.NoticeIntent
import com.arttrip.android.presentation.my.sub.settings.sub.notice.contract.NoticeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel
    @Inject
    constructor(
        private val sessionManager: SessionManager,
    ) : ViewModel() {
        private val _state = MutableStateFlow(NoticeState())
        val state: StateFlow<NoticeState> = _state

        private val _effect = MutableSharedFlow<NoticeEffect>()
        val effect: SharedFlow<NoticeEffect> = _effect

        fun onIntent(intent: NoticeIntent) {
            when (intent) {
                NoticeIntent.BackClicked -> viewModelScope.launch { _effect.emit(NoticeEffect.NavigateBack) }
            }
        }
    }
