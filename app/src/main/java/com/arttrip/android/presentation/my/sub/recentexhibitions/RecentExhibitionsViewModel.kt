package com.arttrip.android.presentation.my.sub.recentexhibitions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.presentation.my.sub.recentexhibitions.contract.RecentExhibitionsEffect
import com.arttrip.android.presentation.my.sub.recentexhibitions.contract.RecentExhibitionsIntent
import com.arttrip.android.presentation.my.sub.recentexhibitions.contract.RecentExhibitionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentExhibitionsViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(RecentExhibitionsState())
        val state = _state.asStateFlow()

        private val _effect = MutableSharedFlow<RecentExhibitionsEffect>()
        val effect = _effect.asSharedFlow()

        fun onIntent(intent: RecentExhibitionsIntent) {
            when (intent) {
                RecentExhibitionsIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(RecentExhibitionsEffect.NavigateBack) }
                }
            }
        }
    }
