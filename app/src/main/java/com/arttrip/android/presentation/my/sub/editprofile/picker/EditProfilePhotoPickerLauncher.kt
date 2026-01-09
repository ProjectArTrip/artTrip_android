package com.arttrip.android.presentation.my.sub.editprofile.picker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun rememberEditProfileAlbumPickerLauncher(onPicked: (Uri) -> Unit): () -> Unit {
    val latestOnPicked by rememberUpdatedState(onPicked)

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) latestOnPicked(uri)
        }

    val request = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

    return { launcher.launch(request) }
}
