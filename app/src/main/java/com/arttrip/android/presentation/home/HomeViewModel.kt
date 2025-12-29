package com.arttrip.android.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.domain.model.network.ApiError
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
import com.arttrip.android.presentation.home.model.SectionLoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
            val country = ForeignCountry.Entire
            val today = LocalDate.now()
            val firstGenre = ExhibitionGenre.ContemporaryArt

            onIntent(HomeIntent.LoadForeignRecommendExhibitList(country))
            onIntent(HomeIntent.LoadForeignPersonalizedExhibitList(country))
            onIntent(HomeIntent.LoadForeignScheduledExhibitList(country, today))
            onIntent(HomeIntent.LoadForeignGenreExhibitList(country, firstGenre))
        }

        fun onIntent(intent: HomeIntent) {
            when (intent) {
                is HomeIntent.SelectTab -> {
                    _state.update { it.copy(placeTabs = intent.tab) }
                }

                is HomeIntent.CountryClicked -> {
                    val newCountry = intent.country

                    _state.update { it.copy(selectedCountry = intent.country) }

                    val date = _state.value.foreignSelectedDate[intent.country.ordinal]
                    val genre = _state.value.foreignSelectedGenre[intent.country.ordinal]

                    loadForeignRecommendExhibitList(newCountry)
                    loadForeignPersonalizedExhibitList(newCountry)
                    loadForeignScheduledExhibitList(newCountry, date)
                    loadForeignGenreExhibitList(newCountry, genre)
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

                is HomeIntent.SelectForeignDate -> {
                    val date = intent.date

                    val country = _state.value.selectedCountry
                    val index = ForeignCountry.entries.indexOf(country)

                    _state.update { state ->
                        state.copy(
                            foreignSelectedDate =
                                state.foreignSelectedDate.mapIndexed { i, old ->
                                    if (i == index) date else old
                                },
                        )
                    }
                }

                is HomeIntent.SelectDomesticDate -> {
                    val date = intent.date

                    _state.update { state ->
                        state.copy(domesticSelectedDate = date)
                    }
                }

                is HomeIntent.SelectForeignGenre -> {
                    val genre = intent.genre

                    val country = _state.value.selectedCountry
                    val index = ForeignCountry.entries.indexOf(country)

                    _state.update { state ->
                        state.copy(
                            foreignSelectedGenre =
                                state.foreignSelectedGenre.mapIndexed { i, old ->
                                    if (i == index) genre else old
                                },
                        )
                    }
                }

                is HomeIntent.SelectDomesticGenre -> {
                    val genre = intent.genre

                    _state.update { state ->
                        state.copy(domesticSelectedGenre = genre)
                    }
                }
                is HomeIntent.ExhibitionClicked -> {
                    viewModelScope.launch {
                        _effect.emit(HomeEffect.NavigateToExhibitionDetail(intent.id))
                    }
                }
            }
        }

//        private fun loadForeignRecommendExhibitList(country: ForeignCountry) {
//            viewModelScope.launch {
//                getForeignRecommendExhibitionListUseCase(country = country)
//                    .collect { result ->
//                        when (result) {
//                            is ApiResult.Loading -> {
//                            }
//
//                            is ApiResult.Success -> {
//                                setRecommend(country = country, list = result.data)
//                            }
//
//                            is ApiResult.Error -> {
//                            }
//                        }
//                    }
//            }
//        }

//        private fun loadForeignPersonalizedExhibitList(country: ForeignCountry) {
//            viewModelScope.launch {
//                getForeignPersonalizedExhibitionListUseCase(country = country)
//                    .collect { result ->
//                        when (result) {
//                            is ApiResult.Loading -> {
//                            }
//
//                            is ApiResult.Success -> {
//                                setPersonalized(country = country, list = result.data)
//                            }
//
//                            is ApiResult.Error -> {
//                            }
//                        }
//                    }
//            }
//        }

//        private fun loadForeignScheduledExhibitList(
//            country: ForeignCountry,
//            date: LocalDate,
//        ) {
//            viewModelScope.launch {
//                getForeignScheduledExhibitionListUseCase(country = country, date = date)
//                    .collect { result ->
//                        when (result) {
//                            is ApiResult.Loading -> {
//                            }
//
//                            is ApiResult.Success -> {
//                                setWeekly(country = country, day = date, list = result.data)
////                                _state.value =
////                                    _state.value.copy(
////                                        interScheduledExhibitList = result.data,
////                                    )
//                            }
//
//                            is ApiResult.Error -> {
//                            }
//                        }
//                    }
//            }
//        }

//        private fun loadForeignGenreExhibitList(
//            country: ForeignCountry,
//            genre: ExhibitionGenre,
//        ) {
//            viewModelScope.launch {
//                getForeignGenreExhibitionListUseCase(country = country, genre = genre)
//                    .collect { result ->
//                        when (result) {
//                            is ApiResult.Loading -> {
//                            }
//
//                            is ApiResult.Success -> {
//                                setByGenre(country = country, genre = genre, list = result.data)
////                            _state.value =
////                                _state.value.copy(
////                                    interScheduledExhibitList = result.data,
////                                )
//                            }
//
//                            is ApiResult.Error -> {
//                            }
//                        }
//                    }
//            }
//        }

        private fun loadDomesticRecommendExhibitList(region: DomesticRegion) {
//            viewModelScope.launch {
//                getDomesticRecommendExhibitionListUseCase(region = region)
//                    .collect { result ->
//                        when (result) {
//                            is ApiResult.Loading -> {
//                            }
//
//                            is ApiResult.Success -> {
//                                setDomesticRecommend(result.data)
//                            }
//
//                            is ApiResult.Error -> {
//                            }
//                        }
//                    }
//            }
        }

        private fun loadDomesticPersonalizedExhibitList(region: DomesticRegion) {
//            viewModelScope.launch {
//                getDomesticPersonalizedExhibitionListUseCase(region = region)
//                    .collect { result ->
//                        when (result) {
//                            is ApiResult.Loading -> {
//                            }
//
//                            is ApiResult.Success -> {
//                                setDomesticPersonalized(result.data)
//                            }
//
//                            is ApiResult.Error -> {
//                            }
//                        }
//                    }
//            }
        }

        private fun loadDomesticScheduledExhibitList(
            region: DomesticRegion,
            date: LocalDate,
        ) {
//            viewModelScope.launch {
//                getDomesticScheduleExhibitionListUseCase(region = region, date = date)
//                    .collect { result ->
//                        when (result) {
//                            is ApiResult.Loading -> {
//                            }
//
//                            is ApiResult.Success -> {
//                                setDomesticWeekly(day = date, list = result.data)
//                            }
//
//                            is ApiResult.Error -> {
//                            }
//                        }
//                    }
//            }
        }

        private fun loadDomesticGenreExhibitList(
            region: DomesticRegion,
            genre: ExhibitionGenre,
        ) {
//            viewModelScope.launch {
//                getDomesticGenreExhibitionListUseCase(region = region, genre = genre)
//                    .collect { result ->
//                        when (result) {
//                            is ApiResult.Loading -> {
//                            }
//
//                            is ApiResult.Success -> {
//                                setDomesticByGenre(genre = genre, list = result.data)
//                            }
//
//                            is ApiResult.Error -> {
//                            }
//                        }
//                    }
//            }
        }

//        fun setRecommend(
//            country: ForeignCountry,
//            list: List<ExhibitionModel>,
//        ) {
//            _state.update { s ->
//                val current = s.foreignExhibitionData.getValue(country)
//
//                s.copy(
//                    foreignExhibitionData =
//                        s.foreignExhibitionData + (
//                            country to current.copy(recommendExhibit = list)
//                        ),
//                )
//            }
//        }
//
//        fun setPersonalized(
//            country: ForeignCountry,
//            list: List<ExhibitionModel>,
//        ) {
//            _state.update { s ->
//                val current = s.foreignExhibitionData.getValue(country)
//
//                s.copy(
//                    foreignExhibitionData =
//                        s.foreignExhibitionData + (
//                            country to current.copy(personalizedList = list)
//                        ),
//                )
//            }
//        }

    private fun loadForeignRecommendExhibitList(country: ForeignCountry) {
        val current = _state.value.foreignExhibitionData.getValue(country).recommendExhibit
        if (current is SectionLoadState.Success || current is SectionLoadState.Loading) return // ✅ 캐시

        setRecommendState(country, SectionLoadState.Loading)

        viewModelScope.launch {
            getForeignRecommendExhibitionListUseCase(country = country).collect { result ->
                when (result) {
                    is ApiResult.Loading -> Unit
                    is ApiResult.Success -> setRecommendState(country, SectionLoadState.Success(result.data))
                    is ApiResult.Error -> setRecommendState(country, SectionLoadState.Error(result.error))
                }
            }
        }
    }

    private fun setRecommendState(
        country: ForeignCountry,
        state: SectionLoadState<List<ExhibitionModel>>,
    ) {
        _state.update { s ->
            val current = s.foreignExhibitionData.getValue(country)
            s.copy(
                foreignExhibitionData = s.foreignExhibitionData + (
                        country to current.copy(recommendExhibit = state)
                        ),
            )
        }
    }

    private fun loadForeignPersonalizedExhibitList(country: ForeignCountry) {
        val current = _state.value.foreignExhibitionData.getValue(country).personalizedList
        if (current is SectionLoadState.Success || current is SectionLoadState.Loading) return // ✅ 캐시

        setPersonalizedState(country, SectionLoadState.Loading)

        viewModelScope.launch {
            getForeignPersonalizedExhibitionListUseCase(country = country).collect { result ->
                when (result) {
                    is ApiResult.Loading -> Unit
                    is ApiResult.Success -> setPersonalizedState(country, SectionLoadState.Success(result.data))
                    is ApiResult.Error -> setPersonalizedState(country, SectionLoadState.Error(result.error))
                }
            }
        }
    }

    private fun setPersonalizedState(
        country: ForeignCountry,
        state: SectionLoadState<List<ExhibitionModel>>,
    ) {
        _state.update { s ->
            val current = s.foreignExhibitionData.getValue(country)
            s.copy(
                foreignExhibitionData = s.foreignExhibitionData + (
                        country to current.copy(personalizedList = state)
                        ),
            )
        }
    }

    private fun loadForeignScheduledExhibitList(country: ForeignCountry, date: LocalDate) {
        val current = _state.value.foreignExhibitionData.getValue(country).scheduleList[date]

        if (current is SectionLoadState.Success || current is SectionLoadState.Loading) return

        setScheduleState(country, date, SectionLoadState.Loading)

        viewModelScope.launch {
            getForeignScheduledExhibitionListUseCase(country, date).collect { result ->
                when (result) {
                    is ApiResult.Loading -> Unit
                    is ApiResult.Success -> setScheduleState(country, date, SectionLoadState.Success(result.data))
                    is ApiResult.Error -> setScheduleState(country, date, SectionLoadState.Error(result.error))
                }
            }
        }
    }

    private fun setScheduleState(
        country: ForeignCountry,
        day: LocalDate,
        state: SectionLoadState<List<ExhibitionModel>>,
    ) {
        _state.update { s ->
            val current = s.foreignExhibitionData.getValue(country)
            s.copy(
                foreignExhibitionData = s.foreignExhibitionData + (
                        country to current.copy(
                            scheduleList = current.scheduleList + (day to state)
                        )
                        )
            )
        }
    }

    private fun loadForeignGenreExhibitList(country: ForeignCountry, genre: ExhibitionGenre) {
        val current = _state.value.foreignExhibitionData.getValue(country).genreList[genre]
        if (current is SectionLoadState.Success || current is SectionLoadState.Loading) return

        setGenreState(country, genre, SectionLoadState.Loading)

        viewModelScope.launch {
            getForeignGenreExhibitionListUseCase(country, genre).collect { result ->
                when (result) {
                    is ApiResult.Loading -> Unit
                    is ApiResult.Success -> setGenreState(country, genre, SectionLoadState.Success(result.data))
                    is ApiResult.Error -> setGenreState(country, genre, SectionLoadState.Error(result.error))
                }
            }
        }
    }

    private fun setGenreState(
        country: ForeignCountry,
        genre: ExhibitionGenre,
        state: SectionLoadState<List<ExhibitionModel>>,
    ) {
        _state.update { s ->
            val current = s.foreignExhibitionData.getValue(country)

            s.copy(
                foreignExhibitionData =
                    s.foreignExhibitionData + (
                            country to current.copy(
                                genreList = current.genreList + (genre to state),
                            )
                            ),
            )
        }
    }

//        fun setByGenre(
//            country: ForeignCountry,
//            genre: ExhibitionGenre,
//            list: List<ExhibitionModel>,
//        ) {
//            _state.update { s ->
//                val current = s.foreignExhibitionData.getValue(country)
//
//                s.copy(
//                    foreignExhibitionData =
//                        s.foreignExhibitionData + (
//                            country to
//                                current.copy(
//                                    genreList = current.genreList + (genre to list),
//                                )
//                        ),
//                )
//            }
//        }

//        /** 1) 오늘/추천 리스트 전체 교체 */
//        private fun setDomesticRecommend(list: List<ExhibitionModel>) {
//            _state.update { s ->
//                s.copy(
//                    domesticExhibitionData =
//                        s.domesticExhibitionData.copy(
//                            recommendExhibit = list,
//                        ),
//                )
//            }
//        }
//
//        /** 2) 개인화 리스트 전체 교체 */
//        private fun setDomesticPersonalized(list: List<ExhibitionModel>) {
//            _state.update { s ->
//                s.copy(
//                    domesticExhibitionData =
//                        s.domesticExhibitionData.copy(
//                            personalizedList = list,
//                        ),
//                )
//            }
//        }
//
//        /** 3) 주간 리스트: 특정 요일만 교체 */
//        private fun setDomesticWeekly(
//            day: LocalDate,
//            list: List<ExhibitionModel>,
//        ) {
//            _state.update { s ->
//                val current = s.domesticExhibitionData
//                s.copy(
//                    domesticExhibitionData =
//                        current.copy(
//                            weeklyList = current.weeklyList + (day to list),
//                        ),
//                )
//            }
//        }
//
//        /** 4) 장르 리스트: 특정 장르만 교체 */
//        private fun setDomesticByGenre(
//            genre: ExhibitionGenre,
//            list: List<ExhibitionModel>,
//        ) {
//            _state.update { s ->
//                val current = s.domesticExhibitionData
//                s.copy(
//                    domesticExhibitionData =
//                        current.copy(
//                            genreList = current.genreList + (genre to list),
//                        ),
//                )
//            }
//        }
//
//        private inline fun updateState(crossinline reducer: (HomeState) -> HomeState) {
//            _state.update { current -> reducer(current) }
//        }
    }
