package com.arttrip.android.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.navigation.main.MainRoute
import com.arttrip.android.presentation.home.contract.HomeEffect

@Composable
fun HomeRoute(
    innerPadding: PaddingValues,
    onNavigate: (String) -> Unit,
    onNavigateRegion: (DomesticRegion) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                HomeEffect.NavigateToNotification -> {
                    onNavigate(MainRoute.HOME_NOTIFICATION)
                }
                HomeEffect.NavigateToDateFilter -> {
                    onNavigate(MainRoute.HOME_DATE_RESULT)
                }
                HomeEffect.NavigateToSearch -> {
                    onNavigate(MainRoute.HOME_SEARCH)
                }
                is HomeEffect.NavigateToExhibitionDetail -> {
                    onNavigate(MainRoute.exhibitionDetail(exhibitId = effect.exhibitionId))
                }

                is HomeEffect.NavigateToRegion -> {
                    onNavigateRegion(effect.region)
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
