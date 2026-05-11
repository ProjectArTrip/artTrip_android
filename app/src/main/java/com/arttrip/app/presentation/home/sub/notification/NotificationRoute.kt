package com.arttrip.app.presentation.home.sub.notification

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.app.presentation.home.sub.notification.contract.NotificationEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NotificationRoute(
    innerPadding: PaddingValues,
    viewModel: NotificationViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val notificationItems = viewModel.notificationsFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                NotificationEffect.NavigateBack -> onBack()
            }
        }
    }

    NotificationScreen(
        innerPadding = innerPadding,
        onIntent = viewModel::onIntent,
        notificationItems = notificationItems,
    )
}
