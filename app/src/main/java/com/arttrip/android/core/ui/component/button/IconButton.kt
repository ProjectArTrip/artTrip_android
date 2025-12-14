package com.arttrip.android.core.ui.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
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
import com.arttrip.android.core.util.noRippleClickable

/**
 * ### 디자인 시스템용 아이콘 버튼 컴포넌트.
 *
 * Material `IconButton`의 기본 최소 터치 영역(48.dp)을 사용하지 않고,
 * 실제 아이콘 크기에 맞는 클릭 영역을 사용하고 싶을 때 사용
 *
 * 리플 효과를 제거하기 위해 [noRippleClickable] 을 사용하며,
 * 아이콘 리소스와 클릭 동작만 주입해서 재사용
 *
 * @param modifier 아이콘에 적용할 [Modifier]
 * @param iconResId 표시할 드로어블 리소스 ID
 * @param contentDescription 접근성용 설명. 필요 없으면 `null`
 * @param tint 아이콘 색상. 기본값은 [Color.Unspecified] 로 원본 색상을 사용
 * @param onIconClick 아이콘 클릭 시 호출되는 콜백
 */
@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified,
    onIconClick: () -> Unit = {},
) {

    val interactionSource = remember { MutableInteractionSource() }

    val rippleIndication = ripple(
        bounded = false,
        radius = 18.dp

    )

    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = rippleIndication,
                onClick = onIconClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            tint = tint,
        )
    }

}

/**
 *### Figma: Btn_upload
 */
@Composable
fun UploadButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
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
    modifier: Modifier = Modifier,
    state: LikeButtonState,
    onClick: () -> Unit,
) {
    Surface(
        modifier =
            modifier
                .size(24.dp)
                .clip(CircleShape)
                .clickable(
                    indication = ripple(bounded = true),
                    interactionSource = remember { MutableInteractionSource() },
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
    modifier: Modifier = Modifier,
    state: HeartButtonState,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val rippleIndication = ripple(
        bounded = false,
        radius = 18.dp

    )

    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = rippleIndication,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center){
        Icon(
            modifier =
                modifier
                    .clip(CircleShape)
                    .clickable(
                        indication = ripple(bounded = true),
                        interactionSource = remember { MutableInteractionSource() },
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

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun AppIconButtonPreview() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AppIconButton(
            modifier =
                Modifier
                    .size(40.dp)
                    .background(Color(0x22000000)),
            iconResId = R.drawable.ic_back_24,
            contentDescription = "뒤로가기",
            tint = Color.Black,
        )

        AppIconButton(
            modifier =
                Modifier
                    .size(40.dp)
                    .background(Color(0x22000000)),
            iconResId = R.drawable.ic_alert_24,
            contentDescription = "알림",
            tint = Color.Black,
        )
    }
}
