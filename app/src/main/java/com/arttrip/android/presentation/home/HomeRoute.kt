package com.arttrip.android.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.navigation.MainRoute
import com.arttrip.android.presentation.home.contract.HomeEffect

@Composable
fun HomeRoute(
    innerPadding: PaddingValues,
    onNavigate: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                HomeEffect.NavigateToDateFilter -> {
                    onNavigate(MainRoute.HOME_DATE_FILTER)
                }
            }
        }
    }
    HomeScreen(
        innerPadding = innerPadding,
        uiState = homeState,
        onIntent = viewModel::onIntent,
    )
}
