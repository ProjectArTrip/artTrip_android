package com.arttrip.android.presentation.mypage.sub.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.ui.launcher.rememberWebLauncher
import com.arttrip.android.core.util.LocalToastController
import com.arttrip.android.presentation.mypage.sub.settings.contract.SettingsEffect
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
    val toast = LocalToastController.current

    val openWeb =
        rememberWebLauncher(
            context = LocalContext.current,
            onFailed = {
                // 토스트 등
            },
        )
    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                SettingsEffect.NavigateBack -> onBack()
                SettingsEffect.NavigateToNotice -> onNavigateNotice()
                SettingsEffect.NavigateToNotification -> onNavigateNotification()
                is SettingsEffect.OpenWeb -> openWeb(effect.url)
                is SettingsEffect.ShowToast -> toast.show(effect.message)
            }
        }
    }
    SettingsScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
