package com.arttrip.android.presentation.home.sub.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.presentation.home.sub.notification.contract.NotificationEffect
import com.arttrip.android.presentation.home.sub.notification.contract.NotificationIntent
import com.arttrip.android.presentation.home.sub.notification.contract.NotificationState
import com.arttrip.android.presentation.home.sub.search.contract.SearchEffect
import com.arttrip.android.presentation.home.sub.search.contract.SearchIntent
import com.arttrip.android.presentation.home.sub.search.contract.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class
NotificationViewModel@Inject
    constructor() : ViewModel() {
    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state

    private val _effect = MutableSharedFlow<NotificationEffect>()
    val effect: SharedFlow<NotificationEffect> = _effect

    fun onIntent(intent: NotificationIntent) {
        when (intent) {
            NotificationIntent.BackClicked -> {
                viewModelScope.launch {
                    _effect.emit(NotificationEffect.NavigateBack)
                }
            }
        }
    }
    }
