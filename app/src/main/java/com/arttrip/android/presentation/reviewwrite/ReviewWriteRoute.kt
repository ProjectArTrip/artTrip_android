package com.arttrip.android.presentation.reviewwrite

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.ui.launcher.PhotoPickerMode
import com.arttrip.android.core.ui.launcher.rememberPhotoPickerLauncher
import com.arttrip.android.presentation.reviewwrite.contract.ReviewWriteEffect
import com.arttrip.android.presentation.reviewwrite.contract.ReviewWriteIntent
import com.arttrip.android.presentation.reviewwrite.model.ReviewWriteMode
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ReviewWriteRoute(
    innerPadding: PaddingValues,
    mode: ReviewWriteMode,
    onBack: () -> Unit,
    onSuccessBack: () -> Unit,
    viewModel: ReviewWriteViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val photoPickerLauncher =
        rememberRemainingPhotoPickerLauncher(
            currentCount = state.photos.size,
            onPicked = { viewModel.onIntent(ReviewWriteIntent.PhotoPickerResult(it)) },
        )
    val latestLaunchPicker by rememberUpdatedState(newValue = photoPickerLauncher)

    LaunchedEffect(mode) {
        viewModel.onIntent(ReviewWriteIntent.Initialize(mode = mode))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { eff ->
            when (eff) {
                ReviewWriteEffect.LaunchPhotoPicker -> {
                    latestLaunchPicker()
                }
                ReviewWriteEffect.NavigateBack -> onBack()
                ReviewWriteEffect.NavigateBackWithSuccess -> onSuccessBack()
            }
        }
    }
    ReviewWriteScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}

@Composable
private fun rememberRemainingPhotoPickerLauncher(
    maxCount: Int = 4,
    currentCount: Int,
    onPicked: (List<Uri>) -> Unit,
): () -> Unit =
    rememberPhotoPickerLauncher(
        mode =
            PhotoPickerMode.MultiRemaining(
                maxCount = maxCount,
                currentCount = currentCount,
            ),
        onPicked = onPicked,
    )
