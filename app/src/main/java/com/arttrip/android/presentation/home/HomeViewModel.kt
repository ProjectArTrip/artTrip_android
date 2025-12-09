package com.arttrip.android.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.home.GetCountryListUseCase
import com.arttrip.android.presentation.home.contract.HomeEffect
import com.arttrip.android.domain.usecase.home.GetHomeRecommendExhibitListUseCase
import com.arttrip.android.domain.usecase.home.international.GetCountryListUseCase
import com.arttrip.android.domain.usecase.home.domestic.GetDomesticPersonalizedExhibitListUseCase
import com.arttrip.android.domain.usecase.home.domestic.GetDomesticRecommendExhibitListUseCase
import com.arttrip.android.domain.usecase.home.international.GetInterPersonalizedExhibitListUseCase
import com.arttrip.android.domain.usecase.home.international.GetInterRecommendExhibitListUseCase
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
        private val getCountryListUseCase: GetCountryListUseCase,
        private val getInterRecommendExhibitListUseCase: GetInterRecommendExhibitListUseCase,
        private val getInterPersonalizedExhibitListUseCase: GetInterPersonalizedExhibitListUseCase,
        private val getDomesticRecommendExhibitListUseCase: GetDomesticRecommendExhibitListUseCase,
        private val getDomesticPersonalizedExhibitListUseCase: GetDomesticPersonalizedExhibitListUseCase
    ) : ViewModel() {
        private val _state = MutableStateFlow(HomeState())
        val state: StateFlow<HomeState> = _state

        private val _effect = MutableSharedFlow<HomeEffect>()
        val effect: SharedFlow<HomeEffect> = _effect

        init {
            // 화면 진입 시 자동 로딩
            onIntent(HomeIntent.LoadCountries)

            onIntent(HomeIntent.LoadInterRecommendExhibitList)
            onIntent(HomeIntent.LoadInterPersonalizedExhibitList)

            onIntent(HomeIntent.LoadDomesticRecommendExhibitList)
            onIntent(HomeIntent.LoadDomesticPersonalizedExhibitList)
        }

        fun onIntent(intent: HomeIntent) {
            when (intent) {
                HomeIntent.LoadCountries,
                HomeIntent.Retry,
                -> loadCountries()

                is HomeIntent.CountryClicked -> {
                    // TODO: 나라 클릭시 처리 (로그, 네비게이션 등)
                }

                is HomeIntent.DateFilterIconClicked -> {
                    viewModelScope.launch {
                        _effect.emit(HomeEffect.NavigateToDateFilter)
                    }
                }
                HomeIntent.LoadInterExhibitList -> loadRecommend(isDomestic = false)
                HomeIntent.LoadInterRecommendExhibitList -> loadInterRecommendExhibitList()
                HomeIntent.LoadInterPersonalizedExhibitList -> loadInterPersonalizedExhibitList()

                HomeIntent.LoadDomesticRecommendExhibitList -> loadDomesticRecommendExhibitList()
                HomeIntent.LoadDomesticPersonalizedExhibitList -> loadDomesticPersonalizedExhibitList()
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
                            _state.value = _state.value.copy(
                                interRecommendExhibitList = result.data
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
                            _state.value = _state.value.copy(
                                interPersonalizedExhibitList = result.data
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
                            _state.value = _state.value.copy(
                                domesticRecommendExhibitList = result.data
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
                            _state.value = _state.value.copy(
                                domesticPersonalizedExhibitList = result.data
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
