package com.arttrip.android.presentation.home.sub.notification

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun NotificationRoute(
    innerPadding: PaddingValues,
    viewModel: NotificationViewModel = hiltViewModel(),
) {
    NotificationScreen(
        innerPadding = innerPadding,
    )
}
