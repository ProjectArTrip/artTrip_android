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
import com.arttrip.android.presentation.home.contract.getThisWeekDatesStartingSunday
import com.arttrip.android.presentation.home.model.ForeignExhibit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
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
            _state.update {
                it.copy(
                    foreignByCountry = initialForeignByCountry()
                )
            }
            onIntent(HomeIntent.LoadForeignRecommendExhibitList(ForeignCountry.Entire))
            onIntent(HomeIntent.LoadForeignPersonalizedExhibitList(ForeignCountry.Entire))
            onIntent(HomeIntent.LoadForeignScheduledExhibitList(ForeignCountry.Entire, LocalDate.now()))
            onIntent(HomeIntent.LoadForeignGenreExhibitList(ForeignCountry.Entire, ExhibitGenre.ContemporaryArt))

            onIntent(HomeIntent.LoadDomesticRecommendExhibitList(DomesticRegion.Entire))
            onIntent(HomeIntent.LoadDomesticPersonalizedExhibitList(DomesticRegion.Entire))
            onIntent(HomeIntent.LoadDomesticScheduledExhibitList(DomesticRegion.Entire, LocalDate.now()))
            onIntent(HomeIntent.LoadDomesticGenreExhibitList(DomesticRegion.Entire, ExhibitGenre.ContemporaryArt))
        }

    private fun emptyForeignExhibit(): ForeignExhibit =
        ForeignExhibit(
            scheduledExhibitList =
                getThisWeekDatesStartingSunday().associateWith { emptyList() },
            genreExhibitMap =
                ExhibitGenre.entries.associateWith { emptyList() }
        )

    private fun initialForeignByCountry(): Map<ForeignCountry, ForeignExhibit> =
        ForeignCountry.entries.associateWith { emptyForeignExhibit() }

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

                is HomeIntent.LoadForeignRecommendExhibitList -> {
                    loadForeignRecommendExhibitList(intent.country)
                }

                is HomeIntent.LoadForeignPersonalizedExhibitList -> {
                    loadForeignPersonalizedExhibitList(intent.country)
                }

                is HomeIntent.LoadForeignScheduledExhibitList -> {
                    loadForeignScheduledExhibitList(intent.country, intent.date)
                }

                is HomeIntent.LoadForeignGenreExhibitList -> {
                    loadForeignGenreExhibitList(intent.country, intent.genre)
                }

                is HomeIntent.LoadDomesticRecommendExhibitList -> {
                    loadDomesticRecommendExhibitList(intent.region)
                }

                is HomeIntent.LoadDomesticPersonalizedExhibitList -> {
                    loadDomesticPersonalizedExhibitList(intent.region)
                }

                is HomeIntent.LoadDomesticScheduledExhibitList -> {
                    loadDomesticScheduledExhibitList(intent.region, intent.date)
                }

                is HomeIntent.LoadDomesticGenreExhibitList -> {
                    loadDomesticGenreExhibitList(intent.region, intent.genre)
                }

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

        private fun loadForeignRecommendExhibitList(country: ForeignCountry) {
            viewModelScope.launch {
                getForeignRecommendExhibitListUseCase(country = country)
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

        private fun loadForeignPersonalizedExhibitList(country: ForeignCountry) {
            viewModelScope.launch {
                getForeignPersonalizedExhibitListUseCase(country = country)
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

        private fun loadForeignScheduledExhibitList(country: ForeignCountry, date: LocalDate) {
            viewModelScope.launch {
                val query = ForeignExhibitListQueryModel(
                    country = "",
                    singleGenre = null,
                    date = ""
                )

                getForeignScheduledExhibitListUseCase(country = country, date = date)
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

    private fun loadForeignGenreExhibitList(country: ForeignCountry, genre: ExhibitGenre) {
        viewModelScope.launch {
            getForeignGenreExhibitListUseCase(country = country, genre = genre)
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

        private fun loadDomesticRecommendExhibitList(region: DomesticRegion) {
            viewModelScope.launch {
                getDomesticRecommendExhibitListUseCase(region = region)
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

        private fun loadDomesticPersonalizedExhibitList(region: DomesticRegion) {
            viewModelScope.launch {
                getDomesticPersonalizedExhibitListUseCase(region = region)
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

        private fun loadDomesticScheduledExhibitList(region: DomesticRegion, date: LocalDate) {
            viewModelScope.launch {
                val query = DomesticExhibitListQueryModel(
                    region = "",
                    singleGenre = null,
                    date = ""
                )

                getDomesticScheduledExhibitListUseCase(region = region, date = date)
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

    private fun loadDomesticGenreExhibitList(region: DomesticRegion, genre: ExhibitGenre) {
        viewModelScope.launch {
            getDomesticGenreExhibitListUseCase(region = region, genre = genre)
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
