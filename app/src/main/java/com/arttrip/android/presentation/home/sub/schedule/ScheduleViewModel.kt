package com.arttrip.android.presentation.home.sub.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.usecase.exhibition.GetScheduleExhibitionUseCase
import com.arttrip.android.presentation.home.sub.schedule.contract.ScheduleEffect
import com.arttrip.android.presentation.home.sub.schedule.contract.ScheduleIntent
import com.arttrip.android.presentation.home.sub.schedule.contract.ScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel
    @Inject
    constructor(
        private val getScheduleExhibitionUseCase: GetScheduleExhibitionUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(ScheduleState())
        val state: StateFlow<ScheduleState> = _state

        private val _effect = MutableSharedFlow<ScheduleEffect>()
        val effect: SharedFlow<ScheduleEffect> = _effect

        private val scheduleTrigger = MutableSharedFlow<ScheduleQueryParams>(replay = 1)

        @OptIn(ExperimentalCoroutinesApi::class)
        val exhibitions: kotlinx.coroutines.flow.Flow<PagingData<Exhibition>> =
            scheduleTrigger
                .flatMapLatest { params ->
                    getScheduleExhibitionUseCase(
                        country = params.country,
                        startDate = params.date.format(DATE_FORMATTER),
                        endDate = params.date.format(DATE_FORMATTER),
                    )
                }.cachedIn(viewModelScope)

        fun onIntent(intent: ScheduleIntent) {
            when (intent) {
                is ScheduleIntent.Initialize -> {
                    val alreadyInitialized = _state.value.selectedDate != null
                    _state.update {
                        if (alreadyInitialized) return@update it
                        it.copy(selectedDate = intent.date, country = intent.country)
                    }
                    if (!alreadyInitialized) {
                        emitTrigger(intent.country, intent.date)
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
                    emitTrigger(_state.value.country, intent.date)
                }

                is ScheduleIntent.ExhibitionClicked -> {
                    viewModelScope.launch {
                        _effect.emit(ScheduleEffect.NavigateToExhibitionDetail(intent.exhibitionId))
                    }
                }
            }
        }

        private fun emitTrigger(
            country: ForeignCountry?,
            date: LocalDate,
        ) {
            viewModelScope.launch {
                scheduleTrigger.emit(ScheduleQueryParams(country = country, date = date))
            }
        }

        private data class ScheduleQueryParams(
            val country: ForeignCountry?,
            val date: LocalDate,
        )

        companion object {
            private val DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        }
    }
