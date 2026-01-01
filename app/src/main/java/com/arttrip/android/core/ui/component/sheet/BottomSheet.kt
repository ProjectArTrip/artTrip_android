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
import androidx.compose.runtime.Stable
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
 * 상단 영역(TopBar) 형태에 따라 두 가지 스타일을 제공합니다.
 *
 * - **DragHandle 타입**: 상단 손잡이 표시 + 시트 드래그 제스처 활성화
 * - **Header 타입**: 상단 닫기(X) 헤더 표시 + 시트 드래그 제스처 비활성화 (바깥 클릭/뒤로가기는 dismiss 가능)
 *
 * 예)
 * ```
 * // 1) DragHandle 타입
 * AppModalBottomSheet(
 *   visible = visible,
 *   onDismissRequest = { visible = false },
 *   topBar = AppBottomSheetTopBar.DragHandle,
 * ) { /* content */ }
 *
 * // 2) Header 타입 (title 선택)
 * AppModalBottomSheet(
 *   visible = visible,
 *   onDismissRequest = { visible = false },
 *   topBar = AppBottomSheetTopBar.Header(title = "바텀시트 제목"),
 * ) { /* content */ }
 * ```
 *
 * @param visible 바텀시트 노출 여부
 * @param onDismissRequest 바깥 영역 클릭/뒤로가기 등 dismiss 요청 콜백
 * @param contentPadding 내부 콘텐츠 패딩 (기본: horizontal 24dp)
 * @param topBar 상단 영역 타입(DragHandle/Header). Header의 title은 topBar 내부에서만 설정
 * @param content 본문 슬롯 (ColumnScope)
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppModalBottomSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp),
    topBar: AppBottomSheetTopBar = AppBottomSheetTopBar.Header(),
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

    val gesturesEnabled = topBar is AppBottomSheetTopBar.DragHandle

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        sheetGesturesEnabled = gesturesEnabled,
        containerColor = AppColor.SubLightGray,
        contentWindowInsets = { WindowInsets(bottom = 0.dp) },
        tonalElevation = 0.dp,
        shape =
            RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
            ),
        dragHandle =
            if (topBar is AppBottomSheetTopBar.DragHandle) {
                { AppBottomSheetDragHandle() }
            } else {
                null
            },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (topBar is AppBottomSheetTopBar.Header) {
                BottomSheetHeader(
                    modifier = Modifier.padding(contentPadding),
                    title = topBar.title,
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

@Stable
sealed interface AppBottomSheetTopBar {
    data object DragHandle : AppBottomSheetTopBar

    data class Header(
        val title: String? = null,
    ) : AppBottomSheetTopBar
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
    name = "AppModalBottomSheet - Compare",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun AppModalBottomSheet_ComparePreview() {
    var visible by remember { mutableStateOf(false) }
    var topBar: AppBottomSheetTopBar by remember { mutableStateOf(AppBottomSheetTopBar.DragHandle) }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AppButton(
                modifier = Modifier.width(160.dp),
                variant = AppButtonVariant.Secondary,
                text = "드래그핸들",
                onClick = {
                    topBar = AppBottomSheetTopBar.DragHandle
                    visible = true
                },
            )
            AppButton(
                modifier = Modifier.width(160.dp),
                variant = AppButtonVariant.Primary,
                text = "헤더",
                onClick = {
                    topBar = AppBottomSheetTopBar.Header(title = "바텀시트 제목")
                    visible = true
                },
            )
        }

        AppModalBottomSheet(
            visible = visible,
            onDismissRequest = { visible = false },
            topBar = topBar,
        ) {
            Text(
                text = if (topBar is AppBottomSheetTopBar.DragHandle) "DragHandle 버전" else "Header 버전",
                color = AppColor.TextPrimary,
            )
            Spacer(Modifier.height(400.dp))
        }
    }
}
