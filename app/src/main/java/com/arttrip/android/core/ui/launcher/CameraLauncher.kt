package com.arttrip.android.core.ui.launcher

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.core.content.FileProvider
import java.io.File

/**
 * 카메라 촬영 결과를 지정한 Uri에 저장하는 런처를 반환.
 *
 * 내부에서 임시 이미지 Uri를 생성한 뒤 `TakePicture()`를 실행하며,
 * 성공 시 해당 Uri를 [onCaptured]로 전달.
 *
 * @param context 임시 파일/Uri 생성을 위한 Context
 * @param authority FileProvider authority (예: "${applicationId}.fileprovider")
 * @param createTempUri 임시 저장 Uri 생성 함수(기본: cacheDir/camera 아래 jpg)
 * @param onCaptured 촬영 성공 시 저장된 Uri
 * @param onFailed 촬영 취소/실패 또는 Uri 생성 실패 시(옵션)
 *
 * @return 호출하면 카메라를 실행하는 lambda
 */
@Composable
fun rememberCameraLauncher(
    context: Context,
    authority: String,
    createTempUri: (context: Context, authority: String) -> Uri? = ::defaultTempJpegUri,
    onCaptured: (Uri) -> Unit,
    onFailed: (() -> Unit)? = null,
): () -> Unit {
    val latestOnCaptured by rememberUpdatedState(onCaptured)
    val latestOnFailed by rememberUpdatedState(onFailed)

    val pendingUriState = remember { mutableStateOf<Uri?>(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            val uri = pendingUriState.value
            pendingUriState.value = null

            if (success && uri != null) latestOnCaptured(uri) else latestOnFailed?.invoke()
        }

    return {
        val uri = createTempUri(context, authority)
        if (uri != null) {
            pendingUriState.value = uri
            launcher.launch(uri)
        } else {
            latestOnFailed?.invoke()
        }
    }
}

private fun defaultTempJpegUri(
    context: Context,
    authority: String,
): Uri? =
    runCatching {
        val dir = File(context.cacheDir, "camera").apply { mkdirs() }
        val file = File(dir, "camera_${System.currentTimeMillis()}.jpg")
        FileProvider.getUriForFile(context, authority, file)
    }.getOrNull()
