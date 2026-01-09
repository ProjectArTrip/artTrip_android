package com.arttrip.android.presentation.reviewwrite

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.reviewwrite.contract.ReviewWriteEffect
import com.arttrip.android.presentation.reviewwrite.contract.ReviewWriteIntent
import com.arttrip.android.presentation.reviewwrite.model.ReviewWritePrefill
import com.arttrip.android.presentation.reviewwrite.picker.rememberReviewPhotoPickerLauncher
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ReviewWriteRoute(
    innerPadding: PaddingValues,
    prefill: ReviewWritePrefill?,
    onBack: () -> Unit,
    viewModel: ReviewWriteViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val photoPickerLauncher =
        rememberReviewPhotoPickerLauncher(
            currentCount = state.photoUris.size,
            onPicked = { viewModel.onIntent(ReviewWriteIntent.PhotoPickerResult(it)) },
        )
    val latestLaunchPicker by rememberUpdatedState(newValue = photoPickerLauncher)

    LaunchedEffect(prefill) {
        if (prefill == null) return@LaunchedEffect
        viewModel.onIntent(ReviewWriteIntent.Initialize(prefill = prefill))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { eff ->
            when (eff) {
                ReviewWriteEffect.LaunchPhotoPicker -> {
                    latestLaunchPicker()
                }
                ReviewWriteEffect.NavigateBack -> onBack()
            }
        }
    }
    ReviewWriteScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
