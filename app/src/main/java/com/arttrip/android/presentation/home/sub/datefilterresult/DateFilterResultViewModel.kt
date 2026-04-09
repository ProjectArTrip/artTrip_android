package com.arttrip.android.presentation.home.sub.datefilterresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.usecase.exhibition.GetCountryExhibitionUseCase
import com.arttrip.android.domain.usecase.exhibition.GetRegionExhibitionUseCase
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultEffect
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultIntent
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultState
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.ExhibitionLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

private data class FilterParams(
    val location: ExhibitionLocation,
    val startDate: LocalDate,
    val endDate: LocalDate,
)

@HiltViewModel
class DateFilterResultViewModel
    @Inject
    constructor(
        private val getRegionExhibitionUseCase: GetRegionExhibitionUseCase,
        private val getCountryExhibitionUseCase: GetCountryExhibitionUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(DateFilterResultState())
        val state: StateFlow<DateFilterResultState> = _state

        private val _effect = MutableSharedFlow<DateFilterResultEffect>()
        val effect: SharedFlow<DateFilterResultEffect> = _effect

        private val filterParams = MutableStateFlow<FilterParams?>(null)

        @OptIn(ExperimentalCoroutinesApi::class)
        val exhibitionsFlow: Flow<PagingData<Exhibition>> =
            filterParams
                .filterNotNull()
                .flatMapLatest { params ->
                    when (val loc = params.location) {
                        is ExhibitionLocation.Domestic -> getRegionExhibitionUseCase(loc.region)
                        is ExhibitionLocation.Foreign -> getCountryExhibitionUseCase(loc.country)
                    }
                }.cachedIn(viewModelScope)

        fun onIntent(intent: DateFilterResultIntent) {
            when (intent) {
                DateFilterResultIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(DateFilterResultEffect.NavigateBack) }
                }

                is DateFilterResultIntent.Initialize -> {
                    val location =
                        if (intent.isDomestic) {
                            ExhibitionLocation.Domestic(DomesticRegion.valueOf(intent.location))
                        } else {
                            ExhibitionLocation.Foreign(ForeignCountry.valueOf(intent.location))
                        }
                    applyFilter(location, intent.startDate, intent.endDate)
                }

                DateFilterResultIntent.DateFilterIconClicked -> {
                    val current = filterParams.value ?: return
                    _state.update {
                        it.copy(
                            isDateFilterSheetVisible = true,
                            dateFilterStartDate = current.startDate,
                            dateFilterEndDate = current.endDate,
                            dateFilterSelectedLocation = current.location,
                        )
                    }
                }

                DateFilterResultIntent.DateFilterSheetDismissed -> {
                    _state.update {
                        it.copy(
                            isDateFilterSheetVisible = false,
                            dateFilterStartDate = null,
                            dateFilterEndDate = null,
                            dateFilterSelectedLocation = null,
                        )
                    }
                }

                DateFilterResultIntent.DateFilterDateSectionOpened -> {
                    if (_state.value.dateFilterStartDate == null) {
                        _state.update { it.copy(dateFilterStartDate = LocalDate.now()) }
                    }
                }

                DateFilterResultIntent.DateFilterResetClicked -> {
                    _state.update {
                        it.copy(
                            dateFilterStartDate = LocalDate.now(),
                            dateFilterEndDate = null,
                        )
                    }
                }

                DateFilterResultIntent.DateFilterApplyClicked -> {
                    val s = _state.value
                    val location = s.dateFilterSelectedLocation ?: return
                    _state.update {
                        it.copy(
                            isDateFilterSheetVisible = false,
                            dateFilterStartDate = null,
                            dateFilterEndDate = null,
                            dateFilterSelectedLocation = null,
                        )
                    }
                    applyFilter(location, s.dateFilterStartDate!!, s.dateFilterEndDate!!)
                }

                is DateFilterResultIntent.DateFilterDayClicked -> {
                    val today = LocalDate.now()
                    val date = intent.date
                    if (date < today) return
                    val startDate = _state.value.dateFilterStartDate ?: today
                    val endDate = _state.value.dateFilterEndDate
                    when {
                        endDate != null -> _state.update { it.copy(dateFilterStartDate = date, dateFilterEndDate = null) }
                        date >= startDate -> _state.update { it.copy(dateFilterEndDate = date) }
                        else -> _state.update { it.copy(dateFilterStartDate = date, dateFilterEndDate = null) }
                    }
                }

                is DateFilterResultIntent.DateFilterLocationSelected -> {
                    _state.update { it.copy(dateFilterSelectedLocation = intent.location) }
                }
            }
        }

        private fun applyFilter(
            location: ExhibitionLocation,
            startDate: LocalDate,
            endDate: LocalDate,
        ) {
            _state.update {
                it.copy(
                    location = location,
                    startDate = startDate,
                    endDate = endDate,
                )
            }
            filterParams.value = FilterParams(location, startDate, endDate)
        }
    }
