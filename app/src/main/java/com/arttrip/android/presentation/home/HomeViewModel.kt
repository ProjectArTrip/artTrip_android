package com.arttrip.android.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.exhibition.GetDomesticGenreExhibitionListUseCase
import com.arttrip.android.domain.usecase.exhibition.GetDomesticPersonalizedExhibitionListUseCase
import com.arttrip.android.domain.usecase.exhibition.GetDomesticRecommendExhibitionListUseCase
import com.arttrip.android.domain.usecase.exhibition.GetDomesticScheduleExhibitionListUseCase
import com.arttrip.android.domain.usecase.exhibition.GetForeignGenreExhibitionListUseCase
import com.arttrip.android.domain.usecase.exhibition.GetForeignPersonalizedExhibitionListUseCase
import com.arttrip.android.domain.usecase.exhibition.GetForeignRecommendExhibitionListUseCase
import com.arttrip.android.domain.usecase.exhibition.GetForeignScheduledExhibitionListUseCase
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
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getForeignRecommendExhibitionListUseCase: GetForeignRecommendExhibitionListUseCase,
        private val getForeignPersonalizedExhibitionListUseCase: GetForeignPersonalizedExhibitionListUseCase,
        private val getForeignScheduledExhibitionListUseCase: GetForeignScheduledExhibitionListUseCase,
        private val getForeignGenreExhibitionListUseCase: GetForeignGenreExhibitionListUseCase,
        private val getDomesticRecommendExhibitionListUseCase: GetDomesticRecommendExhibitionListUseCase,
        private val getDomesticPersonalizedExhibitionListUseCase: GetDomesticPersonalizedExhibitionListUseCase,
        private val getDomesticScheduleExhibitionListUseCase: GetDomesticScheduleExhibitionListUseCase,
        private val getDomesticGenreExhibitionListUseCase: GetDomesticGenreExhibitionListUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(HomeState())
        val state: StateFlow<HomeState> = _state

        private val _effect = MutableSharedFlow<HomeEffect>()
        val effect: SharedFlow<HomeEffect> = _effect

        init {
            onIntent(HomeIntent.LoadForeignRecommendExhibitList(ForeignCountry.Entire))
            onIntent(HomeIntent.LoadForeignPersonalizedExhibitList(ForeignCountry.Entire))
            onIntent(HomeIntent.LoadForeignScheduledExhibitList(ForeignCountry.Entire, LocalDate.now()))
            onIntent(HomeIntent.LoadForeignGenreExhibitList(ForeignCountry.Entire, ExhibitGenre.ContemporaryArt))

            onIntent(HomeIntent.LoadDomesticRecommendExhibitList(DomesticRegion.Entire))
            onIntent(HomeIntent.LoadDomesticPersonalizedExhibitList(DomesticRegion.Entire))
            onIntent(HomeIntent.LoadDomesticScheduledExhibitList(DomesticRegion.Entire, LocalDate.now()))
            onIntent(HomeIntent.LoadDomesticGenreExhibitList(DomesticRegion.Entire, ExhibitGenre.ContemporaryArt))
        }

        fun onIntent(intent: HomeIntent) {
            when (intent) {
                is HomeIntent.SelectTab -> {
                    _state.update { it.copy(placeTabs = intent.tab) }
                }
                is HomeIntent.SelectCountry -> {
                    _state.update { it.copy(selectedCountry = intent.country) }
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

                    val country = _state.value.selectedCountry
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
                getForeignRecommendExhibitionListUseCase(country = country)
                    .collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                            }

                            is ApiResult.Success -> {
                                setRecommend(country = country, list = result.data)
                            }

                            is ApiResult.Error -> {
                            }
                        }
                    }
            }
        }

        private fun loadForeignPersonalizedExhibitList(country: ForeignCountry) {
            viewModelScope.launch {
                getForeignPersonalizedExhibitionListUseCase(country = country)
                    .collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                            }

                            is ApiResult.Success -> {
                                setPersonalized(country = country, list = result.data)
                            }

                            is ApiResult.Error -> {
                            }
                        }
                    }
            }
        }

        private fun loadForeignScheduledExhibitList(
            country: ForeignCountry,
            date: LocalDate,
        ) {
            viewModelScope.launch {
                getForeignScheduledExhibitionListUseCase(country = country, date = date)
                    .collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                            }

                            is ApiResult.Success -> {
                                setWeekly(country = country, day = date.dayOfWeek, list = result.data)
//                                _state.value =
//                                    _state.value.copy(
//                                        interScheduledExhibitList = result.data,
//                                    )
                            }

                            is ApiResult.Error -> {
                            }
                        }
                    }
            }
        }

        private fun loadForeignGenreExhibitList(
            country: ForeignCountry,
            genre: ExhibitGenre,
        ) {
            viewModelScope.launch {
                getForeignGenreExhibitionListUseCase(country = country, genre = genre)
                    .collect { result ->
                        when (result) {
                            is ApiResult.Loading -> {
                            }

                            is ApiResult.Success -> {
                                setByGenre(country = country, genre = genre, list = result.data)
//                            _state.value =
//                                _state.value.copy(
//                                    interScheduledExhibitList = result.data,
//                                )
                            }

                            is ApiResult.Error -> {
                            }
                        }
                    }
            }
        }

        private fun loadDomesticRecommendExhibitList(region: DomesticRegion) {
            viewModelScope.launch {
                getDomesticRecommendExhibitionListUseCase(region = region)
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
                getDomesticPersonalizedExhibitionListUseCase(region = region)
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

        private fun loadDomesticScheduledExhibitList(
            region: DomesticRegion,
            date: LocalDate,
        ) {
            viewModelScope.launch {
                getDomesticScheduleExhibitionListUseCase(region = region, date = date)
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

        private fun loadDomesticGenreExhibitList(
            region: DomesticRegion,
            genre: ExhibitGenre,
        ) {
            viewModelScope.launch {
                getDomesticGenreExhibitionListUseCase(region = region, genre = genre)
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

        fun setRecommend(
            country: ForeignCountry,
            list: List<ExhibitionModel>,
        ) {
            _state.update { s ->
                val current = s.countryData.getValue(country)

                s.copy(
                    countryData =
                        s.countryData + (
                            country to current.copy(recommendExhibit = list)
                        ),
                )
            }
        }

        fun setPersonalized(
            country: ForeignCountry,
            list: List<ExhibitionModel>,
        ) {
            _state.update { s ->
                val current = s.countryData.getValue(country)

                s.copy(
                    countryData =
                        s.countryData + (
                            country to current.copy(personalizedList = list)
                        ),
                )
            }
        }

        fun setWeekly(
            country: ForeignCountry,
            day: DayOfWeek,
            list: List<ExhibitionModel>,
        ) {
            _state.update { s ->
                val current = s.countryData.getValue(country)

                s.copy(
                    countryData =
                        s.countryData + (
                            country to
                                current.copy(
                                    weeklyList = current.weeklyList + (day to list),
                                )
                        ),
                )
            }
        }

        fun setByGenre(
            country: ForeignCountry,
            genre: ExhibitGenre,
            list: List<ExhibitionModel>,
        ) {
            _state.update { s ->
                val current = s.countryData.getValue(country)

                s.copy(
                    countryData =
                        s.countryData + (
                            country to
                                current.copy(
                                    genreList = current.genreList + (genre to list),
                                )
                        ),
                )
            }
        }

        private inline fun updateState(crossinline reducer: (HomeState) -> HomeState) {
            _state.update { current -> reducer(current) }
        }
    }
