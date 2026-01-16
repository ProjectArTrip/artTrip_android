package com.arttrip.android.presentation.my.sub.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.my.sub.settings.contract.SettingsEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    onNavigateNotice: () -> Unit,
    onNavigateNotification: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                SettingsEffect.NavigateBack -> onBack()
                SettingsEffect.NavigateToNotice -> onNavigateNotice()
                SettingsEffect.NavigateToNotification -> onNavigateNotification()
            }
        }
    }
    SettingsScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
