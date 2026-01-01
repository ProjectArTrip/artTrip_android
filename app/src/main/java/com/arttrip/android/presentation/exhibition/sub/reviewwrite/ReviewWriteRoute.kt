package com.arttrip.android.presentation.exhibition.sub.reviewwrite

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract.ReviewWriteIntent
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.model.ReviewWritePrefill

@Composable
fun ReviewWriteRoute(
    innerPadding: PaddingValues,
    prefill: ReviewWritePrefill?,
    onBack: () -> Unit,
    viewModel: ReviewWriteViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(prefill) {
        if (prefill == null) return@LaunchedEffect
        viewModel.onIntent(ReviewWriteIntent.Initialize(prefill = prefill))
    }

    ReviewWriteScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
