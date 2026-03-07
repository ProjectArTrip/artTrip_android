package com.arttrip.android.presentation.reviewwrite.ui

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.button.UploadButton
import com.arttrip.android.core.util.noRippleClickable
import com.arttrip.android.presentation.reviewwrite.contract.MAX_REVIEW_PHOTO_COUNT

@Composable
fun ReviewPhotoRow(
    photoUris: List<Uri>,
    modifier: Modifier = Modifier,
    onUploadClick: () -> Unit,
    onRemoveClick: (index: Int) -> Unit,
    onPhotoClick: (index: Int) -> Unit,
) {
    val shown = photoUris.take(MAX_REVIEW_PHOTO_COUNT)
    val canAddMore = shown.size < MAX_REVIEW_PHOTO_COUNT

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        shown.forEachIndexed { index, uri ->
            ReviewPhotoThumbnail(
                modifier =
                    Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                uri = uri,
                onClick = { onPhotoClick(index) },
                onRemoveClick = { onRemoveClick(index) },
            )
        }

        if (canAddMore) {
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .aspectRatio(1f),
            ) {
                UploadButton(
                    modifier = Modifier.matchParentSize(),
                    onClick = onUploadClick,
                )
            }
        }

        val usedSlots = shown.size + if (canAddMore) 1 else 0
        val emptySlots = MAX_REVIEW_PHOTO_COUNT - usedSlots
        repeat(emptySlots) {
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .aspectRatio(1f),
            )
        }
    }
}

@Composable
private fun ReviewPhotoThumbnail(
    modifier: Modifier,
    uri: Uri,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit,
) {
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier =
            modifier
                .clip(shape)
                .noRippleClickable(true) { onClick() },
    ) {
        ReviewPhotoImage(
            modifier = Modifier.matchParentSize(),
            uri = uri,
        )
        AppIconButton(
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp)
                    .size(32.dp),
            iconResId = R.drawable.ic_close_1_24,
            onIconClick = onRemoveClick,
        )
    }
}

@Composable
private fun ReviewPhotoImage(
    modifier: Modifier = Modifier,
    uri: Uri,
) {
    AsyncImage(
        modifier = modifier,
        model = uri,
        contentDescription = "리뷰 사진",
        contentScale = ContentScale.Crop,
    )
}
