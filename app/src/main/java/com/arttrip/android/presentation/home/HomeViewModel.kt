package com.arttrip.android.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.home.domestic.GetDomesticPersonalizedExhibitListUseCase
import com.arttrip.android.domain.usecase.home.domestic.GetDomesticRecommendExhibitListUseCase
import com.arttrip.android.domain.usecase.home.domestic.GetDomesticScheduledExhibitListUseCase
import com.arttrip.android.domain.usecase.home.international.GetInterPersonalizedExhibitListUseCase
import com.arttrip.android.domain.usecase.home.international.GetInterRecommendExhibitListUseCase
import com.arttrip.android.domain.usecase.home.international.GetInterScheduledExhibitListUseCase
import com.arttrip.android.presentation.home.contract.HomeEffect
import com.arttrip.android.presentation.home.contract.HomeIntent
import com.arttrip.android.presentation.home.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getInterRecommendExhibitListUseCase: GetInterRecommendExhibitListUseCase,
        private val getInterPersonalizedExhibitListUseCase: GetInterPersonalizedExhibitListUseCase,
        private val getInterScheduledExhibitListUseCase: GetInterScheduledExhibitListUseCase,
        private val getDomesticRecommendExhibitListUseCase: GetDomesticRecommendExhibitListUseCase,
        private val getDomesticPersonalizedExhibitListUseCase: GetDomesticPersonalizedExhibitListUseCase,
        private val getDomesticScheduledExhibitListUseCase: GetDomesticScheduledExhibitListUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(HomeState())
        val state: StateFlow<HomeState> = _state

        private val _effect = MutableSharedFlow<HomeEffect>()
        val effect: SharedFlow<HomeEffect> = _effect

        init {
            // 화면 진입 시 자동 로딩
//            onIntent(HomeIntent.LoadCountries)
//
//            onIntent(HomeIntent.LoadInterRecommendExhibitList)
//            onIntent(HomeIntent.LoadInterPersonalizedExhibitList)
//            onIntent(HomeIntent.LoadInterScheduledExhibitList)
//
//            onIntent(HomeIntent.LoadDomesticRecommendExhibitList)
//            onIntent(HomeIntent.LoadDomesticPersonalizedExhibitList)
//            onIntent(HomeIntent.LoadDomesticScheduledExhibitList)
        }

        fun onIntent(intent: HomeIntent) {
            when (intent) {
                is HomeIntent.SelectTab -> {
                    _state.update { it.copy(placeTabs = intent.tab) }
                }
                is HomeIntent.SelectCountry -> {
                    _state.update { it.copy(countryChips = intent.country) }
                }
                HomeIntent.LoadCountries,
                HomeIntent.Retry,
                -> loadCountries()

                is HomeIntent.CountryClicked -> {
                    // TODO: 나라 클릭시 처리 (로그, 네비게이션 등)
                }

                is HomeIntent.AlertIconClicked -> {
                    viewModelScope.launch {
                        _effect.emit(HomeEffect.NavigateToAlert)
                    }
                }

                is HomeIntent.DateFilterIconClicked -> {
                    viewModelScope.launch {
                        _effect.emit(HomeEffect.NavigateToDateFilter)
                    }
                }

                is HomeIntent.SearchIconClicked -> {
                    viewModelScope.launch {
                        _effect.emit(HomeEffect.NavigateToSearch)
                    }
                }

                HomeIntent.LoadInterRecommendExhibitList -> loadInterRecommendExhibitList()
                HomeIntent.LoadInterPersonalizedExhibitList -> loadInterPersonalizedExhibitList()
                HomeIntent.LoadInterScheduledExhibitList -> loadInterScheduledExhibitList("2025-12-12")

                HomeIntent.LoadDomesticRecommendExhibitList -> loadDomesticRecommendExhibitList()
                HomeIntent.LoadDomesticPersonalizedExhibitList -> loadDomesticPersonalizedExhibitList()
                HomeIntent.LoadDomesticScheduledExhibitList -> loadDomesticScheduledExhibitList("2025-12-12")

                is HomeIntent.SelectForeignGenre -> {
                    val genre = intent.genre

                    val country = _state.value.countryChips
                    val index = ForeignCountry.entries.indexOf(country)

                    _state.update { state ->
                        state.copy(
                            foreignGenreChips =
                                state.foreignGenreChips.mapIndexed { i, old ->
                                    if (i == index) genre else old
                                },
                        )
                    }
                }

                is HomeIntent.SelectDomesticGenre -> {
                    val genre = intent.genre

                    _state.update { state ->
                        state.copy(domesticGenreChips = genre)
                    }
                }
            }
        }

        private fun loadInterRecommendExhibitList() {
            viewModelScope.launch {
                getInterRecommendExhibitListUseCase()
                    .collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                            }

                            is ApiResult.Success -> {
                                _state.value =
                                    _state.value.copy(
                                        interRecommendExhibitList = result.data,
                                    )
                            }

                            is ApiResult.Error -> {
                            }
                        }
                    }
            }
        }

        private fun loadInterPersonalizedExhibitList() {
            viewModelScope.launch {
                getInterPersonalizedExhibitListUseCase()
                    .collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                            }

                            is ApiResult.Success -> {
                                _state.value =
                                    _state.value.copy(
                                        interPersonalizedExhibitList = result.data,
                                    )
                            }

                            is ApiResult.Error -> {
                            }
                        }
                    }
            }
        }

        private fun loadInterScheduledExhibitList(date: String) {
            viewModelScope.launch {
                getInterScheduledExhibitListUseCase(date = date)
                    .collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                            }

                            is ApiResult.Success -> {
                                _state.value =
                                    _state.value.copy(
                                        interScheduledExhibitList = result.data,
                                    )
                            }

                            is ApiResult.Error -> {
                            }
                        }
                    }
            }
        }

        private fun loadDomesticRecommendExhibitList() {
            viewModelScope.launch {
                getDomesticRecommendExhibitListUseCase()
                    .collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                            }

                            is ApiResult.Success -> {
                                _state.value =
                                    _state.value.copy(
                                        domesticRecommendExhibitList = result.data,
                                    )
                            }

                            is ApiResult.Error -> {
                            }
                        }
                    }
            }
        }

        private fun loadDomesticPersonalizedExhibitList() {
            viewModelScope.launch {
                getDomesticPersonalizedExhibitListUseCase()
                    .collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                            }

                            is ApiResult.Success -> {
                                _state.value =
                                    _state.value.copy(
                                        domesticPersonalizedExhibitList = result.data,
                                    )
                            }

                            is ApiResult.Error -> {
                            }
                        }
                    }
            }
        }

        private fun loadDomesticScheduledExhibitList(date: String) {
            viewModelScope.launch {
                getDomesticScheduledExhibitListUseCase(date = date)
                    .collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                            }

                            is ApiResult.Success -> {
                                _state.value =
                                    _state.value.copy(
                                        domesticScheduledExhibitList = result.data,
                                    )
                            }

                            is ApiResult.Error -> {
                            }
                        }
                    }
            }
        }

        private fun loadCountries() {
//            viewModelScope.launch {
//                getCountryListUseCase()
//                    .collect { result ->
//                        when (result) {
//                            is ApiResult.Loading -> {
//                                updateState { it.copy(isLoading = true, errorMessage = null) }
//                            }
//
//                            is ApiResult.Success -> {
//                                updateState {
//                                    it.copy(
//                                        isLoading = false,
//                                        countries = result.data,
//                                        errorMessage = null,
//                                    )
//                                }
//                            }
//
//                            is ApiResult.Error -> {
//                                updateState {
//                                    it.copy(
//                                        isLoading = false,
//                                        errorMessage = "알 수 없는 오류가 발생했어요",
//                                    )
//                                }
//                            }
//                        }
//                    }
//            }
        }

        private inline fun updateState(crossinline reducer: (HomeState) -> HomeState) {
            _state.update { current -> reducer(current) }
        }
    }
