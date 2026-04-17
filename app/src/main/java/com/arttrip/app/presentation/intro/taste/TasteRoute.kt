package com.arttrip.app.presentation.intro.taste

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.app.core.navigation.app.AppRoute
import com.arttrip.app.core.util.LocalToastController
import com.arttrip.app.presentation.intro.taste.contract.TasteEffect
import com.arttrip.app.presentation.intro.taste.contract.TasteIntent

@Composable
fun TasteRoute(
    innerPadding: PaddingValues,
    viewModel: TasteViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = LocalToastController.current

    LaunchedEffect(Unit) {
        viewModel.onIntent(TasteIntent.Initialize)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                TasteEffect.NavigateToHome -> {
                    onNavigate(AppRoute.MAIN)
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
