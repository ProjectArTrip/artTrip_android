package com.arttrip.app.presentation.home.sub.notification

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.app.presentation.home.sub.notification.contract.NotificationEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NotificationRoute(
    innerPadding: PaddingValues,
    viewModel: NotificationViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onNavigateToNotice: (referenceId: Int) -> Unit,
) {
    val notificationItems = viewModel.notificationsFlow.collectAsLazyPagingItems()
    val localReadIds by viewModel.localReadIds.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                NotificationEffect.NavigateBack -> onBack()
                is NotificationEffect.NavigateToNotice -> onNavigateToNotice(effect.referenceId)
            }
        }
    }

    NotificationScreen(
        innerPadding = innerPadding,
        onIntent = viewModel::onIntent,
        notificationItems = notificationItems,
        localReadIds = localReadIds,
    )
}
