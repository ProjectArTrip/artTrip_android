package com.arttrip.android.presentation.my.sub.settings.sub.notice

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.my.sub.settings.sub.notice.contract.NoticeEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NoticeRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    viewModel: NoticeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                NoticeEffect.NavigateBack -> onBack()
            }
        }
    }
    NoticeScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
