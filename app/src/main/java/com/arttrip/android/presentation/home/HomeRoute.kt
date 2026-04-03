package com.arttrip.android.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.presentation.home.contract.HomeEffect
import java.time.LocalDate

@Composable
fun HomeRoute(
    innerPadding: PaddingValues,
    onNavigateNotification: () -> Unit,
    onNavigateDateCountryResult: (ForeignCountry, LocalDate, LocalDate) -> Unit,
    onNavigateSearch: () -> Unit,
    onNavigateExhibitionDetail: (Int) -> Unit,
    onNavigateRegion: (DomesticRegion) -> Unit,
    onNavigateSchedule: (ForeignCountry?, LocalDate) -> Unit,
    onNavigateGenre: (ForeignCountry?, ExhibitionGenre) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                HomeEffect.NavigateToNotification -> {
                    onNavigateNotification()
                }
                is HomeEffect.NavigateToDateCountryResult -> {
                    onNavigateDateCountryResult(effect.country, effect.startDate, effect.endDate)
                }
                HomeEffect.NavigateToSearch -> {
                    onNavigateSearch()
                }
                is HomeEffect.NavigateToExhibitionDetail -> {
                    onNavigateExhibitionDetail(effect.exhibitionId)
                }

                is HomeEffect.NavigateToRegion -> {
                    onNavigateRegion(effect.region)
                }

                is HomeEffect.NavigateToForeignSchedule -> {
                    onNavigateSchedule(effect.country, effect.date)
                }
                is HomeEffect.NavigateToDomesticSchedule -> {
                    onNavigateSchedule(null, effect.date)
                }

                is HomeEffect.NavigateToForeignGenre -> {
                    onNavigateGenre(effect.country, effect.genre)
                }
                is HomeEffect.NavigateToDomesticGenre -> {
                    onNavigateGenre(null, effect.genre)
                }
            }
        }
    }
    HomeScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
