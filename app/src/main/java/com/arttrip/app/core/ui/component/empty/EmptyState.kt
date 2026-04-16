package com.arttrip.app.core.ui.component.empty

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arttrip.app.R
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle

/**
 * ### 공용 Empty State 컴포넌트.
 *
 * - 아이콘 + 메시지 조합을 동일한 규격으로 렌더링.
 * - 아이콘은 상단으로부터 [topPadding] 만큼 떨어지고, 수평 중앙 정렬.
 *
 * @param iconResId 빈 상태를 나타내는 아이콘 리소스
 * @param message 아이콘 하단에 표시할 메시지
 */
@Composable
fun AppEmptyState(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    message: String,
    topPadding: Dp = 56.dp,
    messageTopSpacing: Dp = 8.dp,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier.padding(top = topPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Icon(
                painter =
                    androidx.compose.ui.res
                        .painterResource(id = iconResId),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Text(
                text = message,
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextTertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = messageTopSpacing),
            )
        }
    }
}

@Preview(
    name = "AppEmptyState",
    showBackground = true,
    backgroundColor = 0xFFF2F2F2,
    widthDp = 360,
    heightDp = 800,
)
@Composable
private fun AppEmptyStatePhonePreview() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(AppColor.Gray0),
        contentAlignment = Alignment.Center,
    ) {
        AppEmptyState(
            iconResId = R.drawable.ic_empty_bookmark_96,
            message = "즐겨찾기 항목이 없습니다.",
            modifier = Modifier.fillMaxSize(),
        )
    }
}
