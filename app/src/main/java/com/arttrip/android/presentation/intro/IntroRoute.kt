package com.arttrip.android.presentation.intro

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.navigation.app.AppRoute
import com.arttrip.android.core.util.LocalToastController
import com.arttrip.android.presentation.intro.contract.IntroEffect
import com.arttrip.android.presentation.intro.contract.IntroIntent

@Composable
fun IntroRoute(
    innerPadding: PaddingValues,
    viewModel: IntroViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = LocalToastController.current

    LaunchedEffect(Unit) {
        viewModel.onIntent(IntroIntent.Initialize)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                IntroEffect.NavigateToHome -> {
                    onNavigate(AppRoute.MAIN)
                }
                is IntroEffect.ShowError -> {
                    toast.show(effect.message)
                }
            }
        }
    }

    IntroScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
