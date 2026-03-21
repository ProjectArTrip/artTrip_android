package com.arttrip.android.presentation.home.sub.genre

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.presentation.home.sub.genre.contract.GenreEffect
import com.arttrip.android.presentation.home.sub.genre.contract.GenreIntent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GenreRoute(
    innerPadding: PaddingValues,
    viewModel: GenreViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onNavigateNotification: () -> Unit,
    onNavigateExhibitionDetail: (Int) -> Unit,
    country: ForeignCountry?,
    genre: ExhibitionGenre,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val exhibitionList = viewModel.exhibitions.collectAsLazyPagingItems()

    LaunchedEffect(genre) {
        viewModel.onIntent(
            GenreIntent.Initialize(
                country = country,
                genre = genre,
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                GenreEffect.NavigateBack -> onBack()
                GenreEffect.NavigateToNotification -> onNavigateNotification()
                is GenreEffect.NavigateToDetail -> onNavigateExhibitionDetail(effect.exhibitId)
            }
        }
    }

    GenreScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
        country = country,
        genre = genre,
        exhibitionList = exhibitionList,
    )
}