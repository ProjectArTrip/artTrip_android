package com.arttrip.android.core.ui.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.theme.AppColor

/**
 *### Figma: Btn_upload
 */
@Composable
fun UploadButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        enabled = true,
        modifier = modifier.wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = AppColor.SubLightGray,
                contentColor = AppColor.Gray900,
            ),
        contentPadding = PaddingValues(24.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_upload_24),
            tint = Color.Unspecified,
            contentDescription = "upload",
        )
    }
}

enum class LikeButtonState { Like, LikeSelected }

/**
 * ### Figma: icon_like
 * - state: Like / LikeSelected
 */
@Composable
fun LikeButton(
    state: LikeButtonState,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier =
            Modifier
                .size(24.dp)
                .clip(CircleShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                ),
        shape = CircleShape,
        color = AppColor.Gray900.copy(alpha = 0.4f),
    ) {
        Box(
            modifier =
                Modifier
                    .padding(top = 1.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter =
                    painterResource(
                        id =
                            if (state == LikeButtonState.LikeSelected) {
                                R.drawable.ic_heart_filled_red_14
                            } else {
                                R.drawable.ic_heart_outline_white_14
                            },
                    ),
                tint = Color.Unspecified,
                contentDescription = "like button",
            )
        }
    }
}

enum class HeartButtonState { Heart, HeartSelected }

/**
 * ### Figma: icon_heart
 * - state: Like / LikeSelected
 */
@Composable
fun HeartButton(
    state: HeartButtonState,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Icon(
        modifier =
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        painter =
            painterResource(
                id =
                    if (state == HeartButtonState.HeartSelected) {
                        R.drawable.ic_heart_filled_purple_24
                    } else {
                        R.drawable.ic_heart_outline_black_24
                    },
            ),
        tint = Color.Unspecified,
        contentDescription = "heartButton button",
    )
}

@Preview(
    name = "Buttons Preview (Interactive)",
    showBackground = true,
)
@Composable
private fun PreviewButtons_Interactive() {
    var uploadClicked by remember { mutableStateOf(false) }
    var likeSelected by remember { mutableStateOf(false) }
    var heartSelected by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        UploadButton(
            onClick = { uploadClicked = !uploadClicked },
        )

        LikeButton(
            state = if (likeSelected) LikeButtonState.LikeSelected else LikeButtonState.Like,
            onClick = { likeSelected = !likeSelected },
        )

        HeartButton(
            state = if (heartSelected) HeartButtonState.HeartSelected else HeartButtonState.Heart,
            onClick = { heartSelected = !heartSelected },
        )
    }
}
