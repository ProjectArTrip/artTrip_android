package com.arttrip.android.presentation.mypage.sub.settings.sub.notification

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.mypage.sub.settings.sub.notification.contract.NotificationEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NotificationRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    viewModel: NotificationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                NotificationEffect.NavigateBack -> onBack()
            }
        }
    }
    NotificationScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
