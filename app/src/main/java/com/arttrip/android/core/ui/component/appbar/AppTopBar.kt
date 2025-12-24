package com.arttrip.android.core.ui.component.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle

/**
 * ### 기본 상단 AppBar 컴포넌트.
 *
 * - 좌측: 뒤로가기 아이콘 버튼 (옵션)
 * - 중앙: 제목 텍스트 (옵션, [AppTextStyle.Headline] + [AppColor.TextPrimary] 고정)
 * - 우측:
 *    - 액션 아이콘 영역 (Slot)
 *    - 우측 액션 Slot에는 주로 [AppIconButton]을 사용하며, 최대 2개까지 두는 것을 권장
 *    - 우측 액션 아이콘 간 간격은 디자인 가이드에 따라 20.dp로 고정
 *
 * 예시:
 * ```
 * AppTopBar(
 *     title = "전시 상세",
 *     onBackClick = { navController.popBackStack() },
 *     actions = {
 *         AppIconButton(
 *             iconResId = R.drawable.ic_share_24,
 *             contentDescription = "공유"
 *         )
 *         AppIconButton(
 *             iconResId = R.drawable.ic_bookmark_24,
 *             contentDescription = "저장"
 *         )
 *     }
 * )
 * ```
 * @param modifier 상단 AppBar 전체에 적용할 [Modifier]
 * @param title 중앙에 표시할 제목 텍스트. `null`이면 제목을 표시하지 않음
 * @param showBackButton 좌측 뒤로가기 버튼 노출 여부
 * @param onBackClick 뒤로가기 버튼 클릭 시 호출되는 콜백
 * @param actions 우측 액션 영역 Slot. 보통 [AppIconButton]을 전달하여 사용
 */
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 24.dp),
    ) {
        Row(
            modifier =
                Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showBackButton) {
                    AppIconButton(
                        iconResId = R.drawable.ic_back_24,
                        contentDescription = "뒤로가기",
                        onIconClick = onBackClick,
                    )
                } else {
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                actions()
            }
        }

        if (title != null) {
            Text(
                text = title,
                modifier = Modifier.align(Alignment.Center),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = AppTextStyle.Headline,
                color = AppColor.TextPrimary,
            )
        }
    }
}

/**
 * 기본 상태 프리뷰:
 * - 백 버튼 + 제목 + 액션 2개
 */
@Preview(showBackground = true, name = "Default")
@Composable
private fun AppTopBarPreview() {
    AppTopBar(
        title = "전시 상세",
        onBackClick = {},
        actions = {
            AppIconButton(
                iconResId = R.drawable.ic_share_24,
                contentDescription = "공유",
            )
            AppIconButton(
                iconResId = R.drawable.ic_alert_24,
                contentDescription = "저장",
            )
        },
    )
}

/**
 * 제목 없는 상태 프리뷰:
 * - 백 버튼 + 우측 액션만 있는 경우
 */
@Preview(showBackground = true, name = "No Title")
@Composable
private fun AppTopBarPreview_NoTitle() {
    AppTopBar(
        title = null,
        onBackClick = {},
        actions = {
            AppIconButton(
                iconResId = R.drawable.ic_search_24,
                contentDescription = "검색",
            )
        },
    )
}

/**
 * 백 버튼 없는 상태 프리뷰:
 * - 중앙 타이틀만 있는 경우
 */
@Preview(showBackground = true, name = "No Back Button")
@Composable
private fun AppTopBarPreview_NoButton() {
    AppTopBar(
        title = "ARTTRIP",
        showBackButton = false,
        actions = {},
    )
}
