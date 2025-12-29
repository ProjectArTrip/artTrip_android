package com.arttrip.android.core.ui.component.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.button.AppButton
import com.arttrip.android.core.ui.component.button.AppButtonVariant
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable

/**
 * ### 앱 공통 Modal Bottom Sheet 컴포넌트.
 *
 * - **Drag handle 버전**: `showDragHandle = true` (상단 손잡이 표시)
 * - **Header 버전**: `showDragHandle = false` (상단에 닫기(X) 헤더 표시)
 *
 * 예)
 * ```
 * // 1) Drag handle 버전
 * AppModalBottomSheet(
 *   visible = visible,
 *   onDismissRequest = { visible = false },
 *   showDragHandle = true,
 * ) { /* content */ }
 *
 * // 2) Header(X) 버전
 * AppModalBottomSheet(
 *   visible = visible,
 *   onDismissRequest = { visible = false },
 *   showDragHandle = false,
 *   title = "바텀시트 제목",
 * ) { /* content */ }
 * ```
 *
 * @param visible 바텀시트 노출 여부
 * @param onDismissRequest 바깥 영역 클릭/뒤로가기 등 dismiss 요청 콜백
 * @param contentPadding 내부 콘텐츠 패딩 (기본: horizontal 24dp)
 * @param showDragHandle true면 Drag handle 표시, false면 Header(X) 표시
 * @param title Header 버전에서 사용하는 제목 (nullable)
 * @param content 본문 슬롯 (ColumnScope)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppModalBottomSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp),
    showDragHandle: Boolean = false,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (!visible) return
    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )
    LaunchedEffect(Unit) {
        sheetState.show()
    }
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = AppColor.SubLightGray,
        contentWindowInsets = { WindowInsets(bottom = 0.dp) },
        tonalElevation = 0.dp,
        shape =
            RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
            ),
        dragHandle =
            if (showDragHandle) {
                { AppBottomSheetDragHandle() }
            } else {
                null
            },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (!showDragHandle) {
                BottomSheetHeader(
                    modifier = Modifier.padding(contentPadding),
                    title = title,
                    onCloseClick = onDismissRequest,
                )
            }
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(contentPadding),
                content = content,
            )

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .windowInsetsBottomHeight(BottomSheetDefaults.windowInsets)
                        .background(AppColor.SubLightGray),
            )
        }
    }
}

@Composable
private fun BottomSheetHeader(
    modifier: Modifier = Modifier,
    title: String? = null,
    onCloseClick: () -> Unit,
) {
    Row(
        modifier = modifier.padding(top = 16.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!title.isNullOrBlank()) {
            Text(
                text = title,
                style = AppTextStyle.Title02Bold,
                color = AppColor.TextPrimary,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        AppIconButton(
            iconResId = R.drawable.ic_close_24,
            onIconClick = onCloseClick,
        )
    }
}

@Composable
private fun AppBottomSheetDragHandle(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .padding(top = 10.dp)
                .width(32.dp)
                .height(4.dp)
                .background(
                    color = AppColor.Gray100,
                    shape = RoundedCornerShape(10.dp),
                ).noRippleClickable(
                    enabled = true,
                    onClick = {},
                ),
    )
}

@Preview(
    name = "AppModalBottomSheet - Handle vs Header",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun AppModalBottomSheet_ComparePreview() {
    var visible by remember { mutableStateOf(false) }
    var useDragHandle by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AppButton(
                modifier = Modifier.width(160.dp),
                variant = AppButtonVariant.Secondary,
                text = "드래그핸들 시트",
                onClick = {
                    useDragHandle = true
                    visible = true
                },
            )
            AppButton(
                modifier = Modifier.width(160.dp),
                variant = AppButtonVariant.Primary,
                text = "헤더 시트",
                onClick = {
                    useDragHandle = false
                    visible = true
                },
            )
        }

        AppModalBottomSheet(
            visible = visible,
            onDismissRequest = { visible = false },
            showDragHandle = useDragHandle,
            title = if (useDragHandle) null else "바텀시트 제목",
        ) {
            Text(
                text = if (useDragHandle) "DragHandle 버전" else "Header 버전",
                color = AppColor.TextPrimary,
            )

            Spacer(Modifier.height(400.dp))
        }
    }
}
