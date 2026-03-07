package com.arttrip.android.presentation.home.sub.genre

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.core.navigation.main.navigateToNotification
import com.arttrip.android.presentation.home.sub.genre.contract.GenreEffect
import com.arttrip.android.presentation.home.sub.genre.contract.GenreIntent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GenreRoute(
    innerPadding: PaddingValues,
    viewModel: GenreViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onNavigateNotification: () -> Unit,
    country: ForeignCountry?,
    genre: ExhibitionGenre,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(genre) {
        viewModel.onIntent(
            GenreIntent.Initialize(
                genre = genre
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                GenreEffect.NavigateBack -> onBack()
                GenreEffect.NavigateToNotification -> onNavigateNotification()
            }
        }
    }

    GenreScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
        country = country,
        genre = genre,
    )
}
