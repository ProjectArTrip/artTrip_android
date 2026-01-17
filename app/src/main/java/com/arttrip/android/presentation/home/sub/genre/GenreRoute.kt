package com.arttrip.android.presentation.home.sub.genre

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.presentation.home.sub.genre.contract.GenreEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GenreRoute(
    innerPadding: PaddingValues,
    viewModel: GenreViewModel = hiltViewModel(),
    onBack: () -> Unit,
    country: ForeignCountry?,
    genre: ExhibitionGenre
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                GenreEffect.NavigateBack -> onBack()
            }
        }
    }

    GenreScreen(
        innerPadding = innerPadding,
        onIntent = viewModel::onIntent,
        country = country,
        genre = genre
    )
}
