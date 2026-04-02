package com.arttrip.android.presentation.home.sub.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.presentation.home.sub.schedule.contract.ScheduleEffect
import com.arttrip.android.presentation.home.sub.schedule.contract.ScheduleIntent
import com.arttrip.android.presentation.home.sub.schedule.contract.ScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(ScheduleState())
        val state: StateFlow<ScheduleState> = _state

        private val _effect = MutableSharedFlow<ScheduleEffect>()
        val effect: SharedFlow<ScheduleEffect> = _effect

        fun onIntent(intent: ScheduleIntent) {
            when (intent) {
                is ScheduleIntent.Initialize -> {
                    _state.update {
                        if (it.selectedDate != null) return@update it
                        it.copy(selectedDate = intent.date)
                    }
                }

                ScheduleIntent.BackClicked -> {
                    viewModelScope.launch {
                        _effect.emit(ScheduleEffect.NavigateBack)
                    }
                }

                ScheduleIntent.NotificationIconClicked -> {
                    viewModelScope.launch {
                        _effect.emit(ScheduleEffect.NavigateToNotification)
                    }
                }

                ScheduleIntent.OpenFilterSheet -> {
                    _state.update { it.copy(isFilterSheetVisible = true) }
                }

                ScheduleIntent.CloseFilterSheet -> {
                    _state.update { it.copy(isFilterSheetVisible = false) }
                }

                is ScheduleIntent.SelectSortType -> {
                    _state.update { it.copy(selectedSortType = intent.type) }
                }

                is ScheduleIntent.SelectDate -> {
                    _state.update { it.copy(selectedDate = intent.date) }
                }

                is ScheduleIntent.ExhibitionClicked -> {
                    viewModelScope.launch {
                        _effect.emit(ScheduleEffect.NavigateToExhibitionDetail(intent.exhibitionId))
                    }
                }
            }
        }
    }
