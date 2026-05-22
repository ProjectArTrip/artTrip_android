package com.arttrip.app.core.ui.component.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arttrip.app.R
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle

/**
 * 타입마다 로고 크기, corner radius, 문구 유무가 다름.
 *
 * @property Wide 화면 너비 x 280
 * @property P180 180 x 240
 * @property P120 120 x 150
 * @property S100 100 x 100
 * @property S72 72 x 72
 * @property S50 50 x 50
 */
enum class AppImagePlaceholderType {
    Wide,
    P180,
    P120,
    S100,
    S72,
    S50,
}

/**
 * 이미지 로드에 실패했을 때 표시하는 준비중 플레이스홀더.
 *
 * 로고 + 문구 조합이며, [type]에 따라 크기와 corner radius가 결정됨.
 *
 * @param type 사용하는 컴포넌트 크기에 맞는 타입
 */
@Composable
fun AppImagePlaceholder(
    modifier: Modifier = Modifier,
    type: AppImagePlaceholderType,
) {
    val logoWidth: Dp
    val logoHeight: Dp
    val text: String?
    val textSpacing: Dp
    val cornerRadius: Dp

    when (type) {
        AppImagePlaceholderType.Wide -> {
            logoWidth = 126.dp
            logoHeight = 40.dp
            text = "이미지 준비중입니다."
            textSpacing = 16.dp
            cornerRadius = 0.dp
        }
        AppImagePlaceholderType.P180 -> {
            logoWidth = 63.dp
            logoHeight = 20.dp
            text = "이미지 준비중입니다."
            textSpacing = 8.dp
            cornerRadius = 8.dp
        }
        AppImagePlaceholderType.P120 -> {
            logoWidth = 63.dp
            logoHeight = 20.dp
            text = "이미지 준비중입니다."
            textSpacing = 8.dp
            cornerRadius = 8.dp
        }
        AppImagePlaceholderType.S100 -> {
            logoWidth = 63.dp
            logoHeight = 20.dp
            text = "이미지 준비중"
            textSpacing = 8.dp
            cornerRadius = 8.dp
        }
        AppImagePlaceholderType.S72 -> {
            logoWidth = 50.dp
            logoHeight = 16.dp
            text = null
            textSpacing = 0.dp
            cornerRadius = 4.dp
        }
        AppImagePlaceholderType.S50 -> {
            logoWidth = 38.dp
            logoHeight = 12.dp
            text = null
            textSpacing = 0.dp
            cornerRadius = 4.dp
        }
    }

    Box(
        modifier = modifier.background(AppColor.Gray50, RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_logo_black),
                contentDescription = null,
                modifier = Modifier.size(width = logoWidth, height = logoHeight),
                tint = AppColor.TextTertiary,
            )
            if (text != null) {
                Spacer(Modifier.height(textSpacing))
                Text(
                    text = text,
                    style =
                        if (type == AppImagePlaceholderType.Wide) {
                            AppTextStyle.Title02Bold
                        } else {
                            AppTextStyle.Body02Bold
                        },
                    color = AppColor.TextTertiary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppImagePlaceholderPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AppImagePlaceholder(
            modifier = Modifier.fillMaxWidth().height(280.dp),
            type = AppImagePlaceholderType.Wide,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AppImagePlaceholder(
                modifier = Modifier.width(180.dp).height(240.dp),
                type = AppImagePlaceholderType.P180,
            )
            AppImagePlaceholder(
                modifier = Modifier.width(120.dp).height(150.dp),
                type = AppImagePlaceholderType.P120,
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AppImagePlaceholder(
                modifier = Modifier.size(100.dp),
                type = AppImagePlaceholderType.S100,
            )
            AppImagePlaceholder(
                modifier = Modifier.size(72.dp),
                type = AppImagePlaceholderType.S72,
            )
            AppImagePlaceholder(
                modifier = Modifier.size(50.dp),
                type = AppImagePlaceholderType.S50,
            )
        }
    }
}
