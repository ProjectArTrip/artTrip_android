package com.arttrip.android.presentation.home.sub.datefilterresult

import androidx.lifecycle.ViewModel
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultEffect
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultIntent
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DateFilterResultViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(DateFilterResultState())
        val state: StateFlow<DateFilterResultState> = _state

        private val _effect = MutableSharedFlow<DateFilterResultEffect>()
        val effect: SharedFlow<DateFilterResultEffect> = _effect

        fun onIntent(intent: DateFilterResultIntent) {
            when (intent) {
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
                }
            }
        }
    }
