package com.arttrip.app.presentation.splash

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.app.presentation.splash.contract.SplashEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashRoute(
    onNavigate: (String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val activity = LocalActivity.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                SplashEffect.ExitApp -> activity?.finish()
                is SplashEffect.Navigate -> onNavigate(effect.route)
            }
        }
    }

    SplashScreen(
        state = state,
        onIntent = viewModel::onIntent,
    )
}
