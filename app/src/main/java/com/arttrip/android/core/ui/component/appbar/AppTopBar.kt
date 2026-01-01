package com.arttrip.android.core.ui.component.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
 * 좌측/우측 슬롯을 **Slot API**로 제공하는 공통 AppBar
 * 타이틀은 좌/우 버튼 유무와 관계없이 **항상 화면 정가운데**에 정렬
 * 길어질 경우 한 줄 말줄임(Ellipsis) 처리
 *
 * #### 사용 예시
 * 기본(뒤로가기 + 타이틀 + 우측 액션 2개)
 * ```
 * AppTopBar(
 *     title = "전시 상세",
 *     leading = {
 *         AppIconButton(
 *             iconResId = R.drawable.ic_back_24,
 *             contentDescription = "뒤로가기",
 *             onIconClick = { navController.popBackStack() }
 *         )
 *     },
 *     actions = {
 *         AppIconButton(
 *             iconResId = R.drawable.ic_share_24,
 *             contentDescription = "공유",
 *             onIconClick = { /* share */ }
 *         )
 *         AppIconButton(
 *             iconResId = R.drawable.ic_bookmark_24,
 *             contentDescription = "저장",
 *             onIconClick = { /* bookmark */ }
 *         )
 *     }
 * )
 * ```
 *
 * 닫기(X) + 타이틀 (우측 액션 없음)
 * ```
 * AppTopBar(
 *     title = "리뷰 작성",
 *     leading = {
 *         AppIconButton(
 *             iconResId = R.drawable.ic_close_24,
 *             contentDescription = "닫기",
 *             onIconClick = onClose
 *         )
 *     },
 *     actions = null
 * )
 * ```
 *
 * 타이틀만 (leading/actions 모두 없음)
 * ```
 * AppTopBar(
 *     title = "ARTTRIP",
 *     leading = null,
 *     actions = null
 * )
 * ```
 *
 * @param modifier AppBar 전체에 적용할 [Modifier]
 * @param title 중앙에 표시할 제목 텍스트. `null`이면 제목을 표시하지 않음
 * @param leading 좌측 슬롯. 보통 [AppIconButton] 1개를 전달하며 `null`이면 좌측 영역 미표시
 * @param actions 우측 슬롯. [AppIconButton]을 여러 개 전달할 수 있으며 `null`이면 우측 영역이 공간을 차지하지 않음
 */

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    leading: (@Composable () -> Unit)? = {
        AppIconButton(
            iconResId = R.drawable.ic_back_24,
            contentDescription = "뒤로가기",
        )
    },
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (title != null) {
            Text(
                text = title,
                modifier =
                    Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 48.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = AppTextStyle.Headline,
                color = AppColor.TextPrimary,
                textAlign = TextAlign.Center,
            )
        }

        if (leading != null) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leading()
            }
        }

        if (actions != null) {
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                actions()
            }
        }
    }
}

@Preview(showBackground = true, name = "Back + Title + Actions")
@Composable
private fun AppTopBarPreview_Default() {
    AppTopBar(
        title = "전시 상세",
        leading = {
            AppIconButton(
                iconResId = R.drawable.ic_back_24,
                contentDescription = "뒤로가기",
            )
        },
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

@Preview(showBackground = true, name = "Close(X) + Title (No Actions)")
@Composable
private fun AppTopBarPreview_CloseTitleOnly() {
    AppTopBar(
        title = "리뷰 작성",
        leading = {
            AppIconButton(
                iconResId = R.drawable.ic_close_24, // 너희 리소스명에 맞게
                contentDescription = "닫기",
            )
        },
        actions = null,
    )
}

@Preview(showBackground = true, name = "Title Only (No Leading/Actions)")
@Composable
private fun AppTopBarPreview_TitleOnly() {
    AppTopBar(
        title = "ARTTRIP",
        leading = null,
        actions = null,
    )
}

@Preview(showBackground = true, name = "Leading Only (No Title/Actions)")
@Composable
private fun AppTopBarPreview_LeadingOnly() {
    AppTopBar(
        title = null,
        leading = {
            AppIconButton(
                iconResId = R.drawable.ic_back_24,
                contentDescription = "뒤로가기",
            )
        },
        actions = null,
    )
}
