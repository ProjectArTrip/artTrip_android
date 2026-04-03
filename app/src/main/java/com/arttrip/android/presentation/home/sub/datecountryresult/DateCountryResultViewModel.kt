package com.arttrip.android.presentation.home.sub.datecountryresult

import androidx.lifecycle.ViewModel
import com.arttrip.android.presentation.home.sub.datecountryresult.contract.DateCountryResultEffect
import com.arttrip.android.presentation.home.sub.datecountryresult.contract.DateCountryResultIntent
import com.arttrip.android.presentation.home.sub.datecountryresult.contract.DateCountryResultState
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
class DateCountryResultViewModel
    @Inject
    constructor() : ViewModel() {
        private val _state = MutableStateFlow(DateCountryResultState())
        val state: StateFlow<DateCountryResultState> = _state

        private val _effect = MutableSharedFlow<DateCountryResultEffect>()
        val effect: SharedFlow<DateCountryResultEffect> = _effect

        fun onIntent(intent: DateCountryResultIntent) {
            when (intent) {
                is DateCountryResultIntent.Initialize -> {
                    val formatter = DateTimeFormatter.ofPattern("MM.dd (E)", Locale.KOREAN)
                    _state.update {
                        it.copy(
                            location = intent.country.label,
                            dateStr = "${intent.startDate.format(formatter)} - ${intent.endDate.format(formatter)}",
                        )
                    }
                }
            }
        }
    }
