package com.arttrip.app.presentation.stamp

import androidx.lifecycle.ViewModel
import com.arttrip.app.presentation.stamp.contract.StampState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StampViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state =
            MutableStateFlow(StampState())
        val state: StateFlow<StampState> = _state
    }
