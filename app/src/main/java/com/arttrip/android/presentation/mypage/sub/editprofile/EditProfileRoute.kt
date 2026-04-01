package com.arttrip.android.presentation.mypage.sub.editprofile

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.ui.launcher.PhotoPickerMode
import com.arttrip.android.core.ui.launcher.rememberCameraLauncher
import com.arttrip.android.core.ui.launcher.rememberPhotoPickerLauncher
import com.arttrip.android.core.util.LocalToastController
import com.arttrip.android.presentation.mypage.sub.editprofile.contract.EditProfileEffect
import com.arttrip.android.presentation.mypage.sub.editprofile.contract.EditProfileIntent
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@Composable
fun EditProfileRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toast = LocalToastController.current

    val photoPickerLauncher =
        rememberSinglePhotoPickerLauncher(
            onPicked = { uri ->
                viewModel.onIntent(EditProfileIntent.PhotoPickerResult(uri))
            },
        )

    val context = LocalContext.current
    val authority = "${context.packageName}.fileprovider"

    val cameraLauncher =
        rememberEditProfileCameraLauncher(
            context = context,
            authority = authority,
            onCaptured = { uri -> viewModel.onIntent(EditProfileIntent.CameraResult(uri)) },
            onFailed = { viewModel.onIntent(EditProfileIntent.CameraResult(null)) },
        )

    val latestLaunchAlbumPicker by rememberUpdatedState(newValue = photoPickerLauncher)
    val latestLaunchCamera by rememberUpdatedState(newValue = cameraLauncher)

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { eff ->
            when (eff) {
                EditProfileEffect.NavigateBack -> onBack()
                EditProfileEffect.LaunchAlbumPicker -> latestLaunchAlbumPicker()
                EditProfileEffect.LaunchCamera -> latestLaunchCamera()
                is EditProfileEffect.ShowToast -> toast.show(eff.message)
            }
        }
    }

    EditProfileScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}

@Composable
private fun rememberSinglePhotoPickerLauncher(onPicked: (Uri) -> Unit): () -> Unit {
    return rememberPhotoPickerLauncher(
        mode = PhotoPickerMode.Single,
        onPicked = { uris ->
            val uri = uris.firstOrNull() ?: return@rememberPhotoPickerLauncher
            onPicked(uri)
        },
    )
}

@Composable
fun rememberEditProfileCameraLauncher(
    context: Context,
    authority: String,
    onCaptured: (Uri) -> Unit,
    onFailed: (() -> Unit)? = null,
): () -> Unit =
    rememberCameraLauncher(
        context = context,
        authority = authority,
        createTempUri = ::editProfileTempUri,
        onCaptured = onCaptured,
        onFailed = onFailed,
    )

private fun editProfileTempUri(
    context: Context,
    authority: String,
): Uri? =
    runCatching {
        val dir = File(context.cacheDir, "camera").apply { mkdirs() }
        val file = File(dir, "edit_profile_${System.currentTimeMillis()}.jpg")
        FileProvider.getUriForFile(context, authority, file)
    }.getOrNull()
