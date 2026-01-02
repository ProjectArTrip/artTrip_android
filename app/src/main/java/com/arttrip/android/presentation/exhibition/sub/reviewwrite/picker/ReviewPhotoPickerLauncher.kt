package com.arttrip.android.presentation.exhibition.sub.reviewwrite.picker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberUpdatedState

private const val MAX_PHOTO_COUNT = 4

/**
 * 리뷰 작성용: 앨범에서 이미지 "다중 선택" (최대 4장)
 * - 이미 선택된 사진이 있을 때: 기존 + 신규 합쳐서 (중복 허용)
 *
 * @param current 현재 선택된 Uri 리스트(merge 기준)
 * @param onMergedResult merge 결과를 전달
 */

@Composable
fun rememberReviewPhotoPickerLauncher(
    currentCount: Int,
    onPicked: (List<Uri>) -> Unit,
): () -> Unit {
    val latestOnPicked by rememberUpdatedState(onPicked)

    val remaining = (MAX_PHOTO_COUNT - currentCount).coerceAtLeast(0)
    val multiMax = remaining.coerceIn(2, MAX_PHOTO_COUNT)
    val singleLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) latestOnPicked(listOf(uri))
        }

    val multiLauncher =
        key(multiMax) {
            rememberLauncherForActivityResult(
                ActivityResultContracts.PickMultipleVisualMedia(multiMax),
            ) { uris ->
                if (uris.isNotEmpty()) latestOnPicked(uris)
            }
        }

    val request = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

    return {
        when {
            remaining <= 0 -> Unit
            remaining == 1 -> singleLauncher.launch(request)
            else -> multiLauncher.launch(request)
        }
    }
}

private fun mergeAndEmit(
    current: List<Uri>,
    picked: List<Uri>,
    onMergedResult: (List<Uri>) -> Unit,
) {
    if (picked.isEmpty()) return

    val cur = current.distinct()
    val remaining = (MAX_PHOTO_COUNT - cur.size).coerceAtLeast(0)
    if (remaining == 0) return

    val toAppend =
        picked
            .asSequence()
            .take(remaining)
            .toList()

    onMergedResult((cur + toAppend).take(MAX_PHOTO_COUNT))
}
