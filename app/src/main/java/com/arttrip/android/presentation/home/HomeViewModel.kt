package com.arttrip.android.presentation.home

import DomesticExhibitListQueryModel
import ForeignExhibitListQueryModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.home.domestic.GetDomesticGenreExhibitListUseCase
import com.arttrip.android.domain.usecase.home.domestic.GetDomesticPersonalizedExhibitListUseCase
import com.arttrip.android.domain.usecase.home.domestic.GetDomesticRecommendExhibitListUseCase
import com.arttrip.android.domain.usecase.home.domestic.GetDomesticScheduledExhibitListUseCase
import com.arttrip.android.domain.usecase.home.foreign.GetForeignGenreExhibitListUseCase
import com.arttrip.android.domain.usecase.home.foreign.GetForeignPersonalizedExhibitListUseCase
import com.arttrip.android.domain.usecase.home.foreign.GetForeignRecommendExhibitListUseCase
import com.arttrip.android.domain.usecase.home.foreign.GetForeignScheduledExhibitListUseCase
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
        private val getForeignRecommendExhibitListUseCase: GetForeignRecommendExhibitListUseCase,
        private val getForeignPersonalizedExhibitListUseCase: GetForeignPersonalizedExhibitListUseCase,
        private val getForeignScheduledExhibitListUseCase: GetForeignScheduledExhibitListUseCase,
        private val getForeignGenreExhibitListUseCase: GetForeignGenreExhibitListUseCase,
        private val getDomesticRecommendExhibitListUseCase: GetDomesticRecommendExhibitListUseCase,
        private val getDomesticPersonalizedExhibitListUseCase: GetDomesticPersonalizedExhibitListUseCase,
        private val getDomesticScheduledExhibitListUseCase: GetDomesticScheduledExhibitListUseCase,
        private val getDomesticGenreExhibitListUseCase: GetDomesticGenreExhibitListUseCase
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

                HomeIntent.LoadForeignRecommendExhibitList -> loadForeignRecommendExhibitList()
                HomeIntent.LoadForeignPersonalizedExhibitList -> loadForeignPersonalizedExhibitList()
                HomeIntent.LoadForeignScheduledExhibitList -> loadForeignScheduledExhibitList()
                HomeIntent.LoadForeignGenreExhibitList -> loadForeignGenreExhibitList()

                HomeIntent.LoadDomesticRecommendExhibitList -> loadDomesticRecommendExhibitList()
                HomeIntent.LoadDomesticPersonalizedExhibitList -> loadDomesticPersonalizedExhibitList()
                HomeIntent.LoadDomesticScheduledExhibitList -> loadDomesticScheduledExhibitList()
                HomeIntent.LoadDomesticGenreExhibitList -> loadDomesticGenreExhibitList()

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

        private fun loadForeignRecommendExhibitList() {
            viewModelScope.launch {
                val query = ForeignExhibitListQueryModel(
                    country = "",
                    singleGenre = null,
                    date = ""
                )
                getForeignRecommendExhibitListUseCase(query)
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

        private fun loadForeignPersonalizedExhibitList() {
            viewModelScope.launch {
                val query = ForeignExhibitListQueryModel(
                    country = "",
                    singleGenre = null,
                    date = ""
                )

                getForeignPersonalizedExhibitListUseCase(query = query)
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

        private fun loadForeignScheduledExhibitList() {
            viewModelScope.launch {
                val query = ForeignExhibitListQueryModel(
                    country = "",
                    singleGenre = null,
                    date = ""
                )

                getForeignScheduledExhibitListUseCase(query = query)
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

    private fun loadForeignGenreExhibitList() {
        viewModelScope.launch {
            val query = ForeignExhibitListQueryModel(
                country = "",
                singleGenre = null,
                date = ""
            )

            getForeignGenreExhibitListUseCase(query = query)
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
                val query = DomesticExhibitListQueryModel(
                    region = "",
                    singleGenre = null,
                    date = ""
                )

                getDomesticRecommendExhibitListUseCase(query)
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
                val query = DomesticExhibitListQueryModel(
                    region = "",
                    singleGenre = null,
                    date = ""
                )

                getDomesticPersonalizedExhibitListUseCase(query = query)
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

        private fun loadDomesticScheduledExhibitList() {
            viewModelScope.launch {
                val query = DomesticExhibitListQueryModel(
                    region = "",
                    singleGenre = null,
                    date = ""
                )

                getDomesticScheduledExhibitListUseCase(query = query)
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

    private fun loadDomesticGenreExhibitList() {
        viewModelScope.launch {
            val query = DomesticExhibitListQueryModel(
                region = "",
                singleGenre = null,
                date = ""
            )

            getDomesticGenreExhibitListUseCase(query = query)
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

        private inline fun updateState(crossinline reducer: (HomeState) -> HomeState) {
            _state.update { current -> reducer(current) }
        }
    }
