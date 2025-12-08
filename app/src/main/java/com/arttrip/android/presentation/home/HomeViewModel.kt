package com.arttrip.android.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.home.GetCountryListUseCase
import com.arttrip.android.presentation.home.contract.HomeEffect
import com.arttrip.android.domain.usecase.home.GetHomeRecommendExhibitListUseCase
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
        private val getHomeRecommendExhibitListUseCase: GetHomeRecommendExhibitListUseCase
    ) : ViewModel() {
        private val _state = MutableStateFlow(HomeState())
        val state: StateFlow<HomeState> = _state

        private val _effect = MutableSharedFlow<HomeEffect>()
        val effect: SharedFlow<HomeEffect> = _effect

        init {
            // 화면 진입 시 자동 로딩
            onIntent(HomeIntent.LoadCountries)
            onIntent(HomeIntent.LoadInterExhibitList)
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
            }
        }

    private fun loadRecommend(isDomestic: Boolean) {
        viewModelScope.launch {
            getHomeRecommendExhibitListUseCase(isDomestic)
                .collect { result ->
                    Log.d("hyunjun", result.toString())
                    when (result) {
                        is ApiResult.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = true,
                                errorMessage = null,
                            )
                        }

                        is ApiResult.Success -> {
                            Log.d("hyunjun", result.data.toString())
                            _state.value = _state.value.copy(
                                isLoading = false,
                                interRecommendList = result.data,
                                errorMessage = null,
                            )
                        }

                        is ApiResult.Error -> {

                        }
                    }
                }
        }
    }

        private fun loadCountries() {
            viewModelScope.launch {
                getCountryListUseCase()
                    .collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                                updateState { it.copy(isLoading = true, errorMessage = null) }
                            }

                            is ApiResult.Success -> {
                                updateState {
                                    it.copy(
                                        isLoading = false,
                                        countries = result.data,
                                        errorMessage = null,
                                    )
                                }
                            }

                            is ApiResult.Error -> {
                                updateState {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = "알 수 없는 오류가 발생했어요",
                                    )
                                }
                            }
                        }
                    }
            }
        }

        private inline fun updateState(crossinline reducer: (HomeState) -> HomeState) {
            _state.update { current -> reducer(current) }
        }
    }
