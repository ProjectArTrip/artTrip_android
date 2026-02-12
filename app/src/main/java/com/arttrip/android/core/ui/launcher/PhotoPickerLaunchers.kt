package com.arttrip.android.core.ui.launcher

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberUpdatedState

sealed interface PhotoPickerMode {
    /** 항상 1장만 선택하는 모드 */
    data object Single : PhotoPickerMode

    /**
     * 최대 [maxCount]장까지 허용하는 "추가 선택" 모드.
     * - 이미 [currentCount]장이 선택되어 있으면, 남은 장수(remaining)만큼만 추가로 선택 가능
     * - remaining == 1 이면 단일 선택 picker로 동작
     * - remaining >= 2 이면 다중 선택 picker로 동작
     */
    data class MultiRemaining(
        val maxCount: Int,
        val currentCount: Int,
        val minMultiPick: Int = 2,
    ) : PhotoPickerMode
}

/**
 * Android Photo Picker 런처를 공용화한 유틸.
 *
 * @param mode 선택 방식(단일 / 남은 장수 기반 다중 선택)
 * @param onPicked 선택 결과(Uri 리스트). 취소 시 호출되지 않습니다.
 *
 * @return 호출하면 Photo Picker를 실행하는 lambda
 */
@Composable
fun rememberPhotoPickerLauncher(
    mode: PhotoPickerMode,
    onPicked: (List<Uri>) -> Unit,
): () -> Unit {
    val latestOnPicked by rememberUpdatedState(onPicked)

    val request = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

    val singleLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) latestOnPicked(listOf(uri))
        }

    val remaining: Int
    val multiMax: Int
    val multiLauncher =
        when (mode) {
            PhotoPickerMode.Single -> {
                remaining = 0
                multiMax = 0
                null
            }

            is PhotoPickerMode.MultiRemaining -> {
                remaining = (mode.maxCount - mode.currentCount).coerceAtLeast(0)
                multiMax = remaining.coerceIn(mode.minMultiPick, mode.maxCount)

                key(multiMax) {
                    rememberLauncherForActivityResult(
                        ActivityResultContracts.PickMultipleVisualMedia(multiMax),
                    ) { uris ->
                        if (uris.isNotEmpty()) latestOnPicked(uris)
                    }
                }
            }
        }

    return {
        when (mode) {
            PhotoPickerMode.Single -> {
                singleLauncher.launch(request)
            }

            is PhotoPickerMode.MultiRemaining -> {
                when {
                    remaining <= 0 -> Unit
                    remaining == 1 -> singleLauncher.launch(request)
                    else -> multiLauncher?.launch(request)
                }
            }
        }
    }
}
