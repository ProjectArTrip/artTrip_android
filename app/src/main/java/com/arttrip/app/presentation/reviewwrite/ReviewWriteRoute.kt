package com.arttrip.app.presentation.reviewwrite

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.app.core.ui.launcher.PhotoPickerMode
import com.arttrip.app.core.ui.launcher.rememberPhotoPickerLauncher
import com.arttrip.app.core.util.LocalToastController
import com.arttrip.app.presentation.reviewwrite.contract.ReviewWriteEffect
import com.arttrip.app.presentation.reviewwrite.contract.ReviewWriteIntent
import com.arttrip.app.presentation.reviewwrite.model.ReviewWriteMode
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
    val toast = LocalToastController.current

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
                is ReviewWriteEffect.NavigateBackWithToast -> {
                    toast.show(eff.message)
                    onSuccessBack()
                }
                is ReviewWriteEffect.ShowToast -> toast.show(eff.message)
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
