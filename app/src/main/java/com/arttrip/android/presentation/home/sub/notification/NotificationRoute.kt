package com.arttrip.android.presentation.home.sub.notification

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.arttrip.android.presentation.home.sub.notification.contract.NotificationEffect
import com.arttrip.android.presentation.home.sub.search.contract.SearchEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NotificationRoute(
    innerPadding: PaddingValues,
    viewModel: NotificationViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                NotificationEffect.NavigateBack -> onBack()
            }
        }
    }

    NotificationScreen(
        innerPadding = innerPadding,
        onIntent = viewModel::onIntent
    )
}
