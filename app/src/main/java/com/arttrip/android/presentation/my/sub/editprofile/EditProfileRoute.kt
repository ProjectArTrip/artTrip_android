package com.arttrip.android.presentation.my.sub.editprofile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.BuildConfig
import com.arttrip.android.presentation.my.sub.editprofile.contract.EditProfileEffect
import com.arttrip.android.presentation.my.sub.editprofile.contract.EditProfileIntent
import com.arttrip.android.presentation.my.sub.editprofile.picker.rememberEditProfileAlbumPickerLauncher
import com.arttrip.android.presentation.my.sub.editprofile.picker.rememberEditProfileCameraLauncher
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditProfileRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val photoPickerLauncher =
        rememberEditProfileAlbumPickerLauncher(
            onPicked = { uri ->
                viewModel.onIntent(EditProfileIntent.PhotoPickerResult(uri))
            },
        )
    val latestLaunchAlbumPicker by rememberUpdatedState(newValue = photoPickerLauncher)

    val context = LocalContext.current
    val cameraLauncher =
        rememberEditProfileCameraLauncher(
            context = context,
            authority = BuildConfig.APPLICATION_ID + ".fileprovider",
            onCaptured = { uri ->
                viewModel.onIntent(EditProfileIntent.CameraResult(uri))
            },
            onFailed = {
                viewModel.onIntent(EditProfileIntent.CameraResult(null))
            },
        )
    val latestLaunchCamera by rememberUpdatedState(newValue = cameraLauncher)

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { eff ->
            when (eff) {
                EditProfileEffect.NavigateBack -> onBack()
                EditProfileEffect.LaunchAlbumPicker -> latestLaunchAlbumPicker()
                EditProfileEffect.LaunchCamera -> latestLaunchCamera()
            }
        }
    }

    EditProfileScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
