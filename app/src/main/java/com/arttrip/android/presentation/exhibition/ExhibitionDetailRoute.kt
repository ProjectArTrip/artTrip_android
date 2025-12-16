package com.arttrip.android.presentation.exhibition

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailEffect
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailIntent

@Composable
fun ExhibitionDetailRoute(
    innerPadding: PaddingValues,
    exhibitId: Int,
    onBack: () -> Unit,
    viewModel: ExhibitionDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(ExhibitionDetailIntent.Initialize(exhibitId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ExhibitionDetailEffect.NavigateBack -> {
                    onBack()
                }
                is ExhibitionDetailEffect.ShowError -> {
                    // 스낵바 / 토스트 등
                }
            }
        }
    }

    ExhibitionDetailScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
