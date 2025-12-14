package com.arttrip.android.presentation.exhibition

import androidx.lifecycle.ViewModel
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailEffect
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailIntent
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExhibitionDetailViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(ExhibitionDetailState())
        val state: StateFlow<ExhibitionDetailState> = _state

        private val _effect = MutableSharedFlow<ExhibitionDetailEffect>()
        val effect: SharedFlow<ExhibitionDetailEffect> = _effect

        fun onIntent(intent: ExhibitionDetailIntent) {
        }
    }
