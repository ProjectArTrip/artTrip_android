package com.arttrip.app.core.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.arttrip.app.core.ui.component.button.AppButton
import com.arttrip.app.core.ui.component.button.AppButtonVariant
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.core.ui.theme.Pretendard

/**
 * ### 앱 공통 2-버튼 다이얼로그.
 *
 *
 * 예)
 * ```
 * AppTwoButtonDialog(
 *   visible = visible,
 *   onDismissRequest = { visible = false }, // 뒤로가기/바깥 클릭 시 닫힘
 *   primaryText = "확인",
 *   onPrimaryClick = { /* ... */ },
 *   secondaryText = "취소",
 *   onSecondaryClick = { visible = false },
 * ) { /* ColumnScope content */ }
 * ```
 *
 * @param visible 다이얼로그 노출 여부
 * @param onDismissRequest 바깥 영역 클릭/뒤로가기 등 dismiss 요청 콜백
 * @param contentTopPadding 본문 상단 패딩 (기본 32dp)
 * @param contentBottomPadding 본문 하단 패딩 (기본 24dp)
 *
 * @param content 본문 슬롯 (**ColumnScope**)
 */
@Composable
fun AppTwoButtonDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    primaryText: String,
    onPrimaryClick: () -> Unit,
    primaryEnabled: Boolean = true,
    secondaryText: String,
    onSecondaryClick: () -> Unit,
    secondaryEnabled: Boolean = true,
    contentTopPadding: Dp = 32.dp,
    contentBottomPadding: Dp = 24.dp,
    content: @Composable (ColumnScope.() -> Unit),
) {
    if (!visible) return

    Dialog(
        onDismissRequest = onDismissRequest,
        properties =
            DialogProperties(
                usePlatformDefaultWidth = false,
            ),
    ) {
        Surface(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(16.dp),
            color = AppColor.Gray0,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                        .padding(top = contentTopPadding, bottom = contentBottomPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                content()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    AppButton(
                        modifier = Modifier.weight(1f),
                        variant = AppButtonVariant.Secondary,
                        text = secondaryText,
                        onClick = onSecondaryClick,
                        enabled = secondaryEnabled,
                    )

                    AppButton(
                        modifier = Modifier.weight(1f),
                        variant = AppButtonVariant.Primary,
                        text = primaryText,
                        onClick = onPrimaryClick,
                        enabled = primaryEnabled,
                    )
                }
            }
        }
    }
}

@Composable
fun AppSingleButtonDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    primaryText: String,
    onPrimaryClick: () -> Unit,
    primaryEnabled: Boolean = true,
    contentTopPadding: Dp = 32.dp,
    contentBottomPadding: Dp = 24.dp,
    content: @Composable (ColumnScope.() -> Unit),
) {
    if (!visible) return

    Dialog(
        onDismissRequest = onDismissRequest,
        properties =
            DialogProperties(
                usePlatformDefaultWidth = false,
            ),
    ) {
        Surface(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(16.dp),
            color = AppColor.Gray0,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                        .padding(top = contentTopPadding, bottom = contentBottomPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                content()

                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    variant = AppButtonVariant.Primary,
                    text = primaryText,
                    onClick = onPrimaryClick,
                    enabled = primaryEnabled,
                )
            }
        }
    }
}

@Preview(
    name = "AppTwoButtonDialog Preview",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun AppTwoButtonDialogComparePreview() {
    var visible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AppButton(
            modifier = Modifier.width(160.dp),
            variant = AppButtonVariant.Primary,
            text = "다이얼로그 열기",
            onClick = { visible = true },
        )

        AppTwoButtonDialog(
            visible = visible,
            onDismissRequest = { visible = false },
            primaryText = "리뷰 작성",
            onPrimaryClick = { visible = false },
            secondaryText = "취소",
            onSecondaryClick = { visible = false },
        ) {
            Text(
                text = "리뷰 쓰고 스탬프 받기",
                style =
                    TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = Pretendard,
                        fontWeight = FontWeight(700),
                        color = AppColor.TextPrimary,
                        textAlign = TextAlign.Center,
                    ),
            )

            Spacer(Modifier.height(11.dp))

            HorizontalDivider(
                thickness = 1.dp,
                color = AppColor.Gray50,
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "전시 리뷰를 작성하시면\n해당 국가 스탬프가 자동 발급됩니다.",
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "리뷰를 작성하시겠습니까?",
                style = AppTextStyle.Body01Bold,
                color = AppColor.TextPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}
