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
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

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

        private val initParams = MutableStateFlow<DateFilterResultIntent.Initialize?>(null)

        @OptIn(ExperimentalCoroutinesApi::class)
        val exhibitionsFlow: Flow<PagingData<Exhibition>> =
            initParams
                .filterNotNull()
                .flatMapLatest { params ->
                    if (params.isDomestic) {
                        getRegionExhibitionUseCase(DomesticRegion.valueOf(params.location))
                    } else {
                        getCountryExhibitionUseCase(ForeignCountry.valueOf(params.location))
                    }
                }.cachedIn(viewModelScope)

        fun onIntent(intent: DateFilterResultIntent) {
            when (intent) {
                DateFilterResultIntent.BackClicked -> {
                    viewModelScope.launch { _effect.emit(DateFilterResultEffect.NavigateBack) }
                }
                is DateFilterResultIntent.Initialize -> {
                    val formatter = DateTimeFormatter.ofPattern("MM.dd (E)", Locale.KOREAN)
                    val locationLabel =
                        if (intent.isDomestic) {
                            DomesticRegion.valueOf(intent.location).label
                        } else {
                            ForeignCountry.valueOf(intent.location).label
                        }
                    _state.update {
                        it.copy(
                            location = locationLabel,
                            dateStr = "${intent.startDate.format(formatter)} - ${intent.endDate.format(formatter)}",
                        )
                    }
                    initParams.value = intent
                }
            }
        }
    }
