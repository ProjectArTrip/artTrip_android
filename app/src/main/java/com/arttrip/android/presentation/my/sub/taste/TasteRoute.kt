package com.arttrip.android.presentation.my.sub.taste

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.util.LocalToastController
import com.arttrip.android.presentation.my.sub.taste.contract.TasteEffect
import com.arttrip.android.presentation.my.sub.taste.contract.TasteIntent

@Composable
fun TasteRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    viewModel: TasteViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = LocalToastController.current

    LaunchedEffect(Unit) {
        viewModel.onIntent(TasteIntent.Initialize)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                TasteEffect.NavigateBack -> {
                    onBack()
                }
                is TasteEffect.ShowToastAndNavigateBack -> {
                    toast.show(effect.message)
                    onBack()
                }
                is TasteEffect.ShowError -> {
                    toast.show(effect.message)
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
