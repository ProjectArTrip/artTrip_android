package com.arttrip.android.presentation.home.sub.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.presentation.home.sub.genre.contract.GenreEffect
import com.arttrip.android.presentation.home.sub.genre.contract.GenreIntent
import com.arttrip.android.presentation.home.sub.genre.contract.GenreState
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
GenreViewModel@Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(GenreState())
        val state: StateFlow<GenreState> = _state

        private val _effect = MutableSharedFlow<GenreEffect>()
        val effect: SharedFlow<GenreEffect> = _effect

        fun onIntent(intent: GenreIntent) {
            when (intent) {
                is GenreIntent.Initialize -> {
                    _state.update {
                        if (it.selectedGenre != null) return@update it

                        it.copy(
                            selectedGenre = intent.genre
                        )
                    }
                }
                GenreIntent.BackClicked -> {
                    viewModelScope.launch {
                        _effect.emit(GenreEffect.NavigateBack)
                    }
                }

                GenreIntent.NotificationIconClicked -> {
                    viewModelScope.launch {
                        _effect.emit(GenreEffect.NavigateToNotification)
                    }
                }

                GenreIntent.OpenFilterSheet -> {
                    _state.update { it.copy(isFilterSheetVisible = true) }
                }

                GenreIntent.CloseFilterSheet -> {
                    _state.update { it.copy(isFilterSheetVisible = false) }
                }

                is GenreIntent.SelectSortType -> {
                    _state.update { it.copy(selectedSortType = intent.type) }
                }

                is GenreIntent.SelectGenre -> {
                    _state.update { it.copy(selectedGenre = intent.genre) }
                }
            }
        }
    }
