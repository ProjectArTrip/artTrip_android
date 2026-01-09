package com.arttrip.android.presentation.my.sub.editprofile.picker

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.core.content.FileProvider
import java.io.File

/**
 * EditProfile 전용: 카메라 촬영 → 미리 만든 Uri로 저장
 *
 * @param context FileProvider Uri 생성용
 * @param authority AndroidManifest의 FileProvider authority (예: "${applicationId}.fileprovider")
 * @param onCaptured 촬영 성공 시 Uri 전달
 * @param onFailed 촬영 실패/취소 시 호출(옵션)
 */
@Composable
fun rememberEditProfileCameraLauncher(
    context: Context,
    authority: String,
    onCaptured: (Uri) -> Unit,
    onFailed: (() -> Unit)? = null,
): () -> Unit {
    val latestOnCaptured by rememberUpdatedState(onCaptured)
    val latestOnFailed by rememberUpdatedState(onFailed)

    // 촬영 요청 시마다 새 Uri를 만들기 위해 holder를 씀
    var pendingUri: Uri? = null

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            val uri = pendingUri
            pendingUri = null

            if (success && uri != null) latestOnCaptured(uri) else latestOnFailed?.invoke()
        }

    return {
        pendingUri = createTempImageUri(context, authority)
        pendingUri?.let { launcher.launch(it) } ?: latestOnFailed?.invoke()
    }
}

private fun createTempImageUri(
    context: Context,
    authority: String,
): Uri? =
    runCatching {
        val dir = File(context.cacheDir, "camera").apply { mkdirs() }
        val file = File(dir, "edit_profile_${System.currentTimeMillis()}.jpg")
        FileProvider.getUriForFile(context, authority, file)
    }.getOrNull()
