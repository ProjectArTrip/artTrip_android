package com.arttrip.android.presentation.my.sub.taste

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.my.sub.taste.contract.TasteEffect
import com.arttrip.android.presentation.my.sub.taste.contract.TasteIntent

@Composable
fun TasteRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    viewModel: TasteViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(TasteIntent.Initialize)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                TasteEffect.NavigateBack -> onBack()
                is TasteEffect.ShowError -> {
                    // 스낵바 / 토스트 등
                }
            }
        }
    }

    TasteScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
