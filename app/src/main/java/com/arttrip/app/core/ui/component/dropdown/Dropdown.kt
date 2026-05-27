package com.arttrip.app.core.ui.component.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arttrip.app.R
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.core.util.noRippleClickable

/**
 *### Figma: Dropdown 1
 * @param items 드롭다운 항목 리스트
 * @param selectedIndex 현재 선택된 인덱스 (선택 전이면 -1)
 * @param onSelect 항목 선택 콜백. 인덱스를 그대로 전달.
 * @param modifier 외부에서 전달받는 Modifier
 * @param placeholder 선택 전 표시 텍스트
 * @param enabled 헤더 클릭 가능 여부
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDropdown(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedIndex: Int,
    onSelect: (index: Int) -> Unit,
    placeholder: String = "선택",
    enabled: Boolean = true,
) {
    val shape = AppDropdownDefaults.Shape

    val isSelected = selectedIndex in items.indices
    val displayText = if (isSelected) items[selectedIndex] else placeholder
    val textStyle = AppTextStyle.Body01Regular
    val textColor = AppDropdownDefaults.TextColor

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (enabled) {
                expanded = !expanded
            }
        },
        modifier = modifier,
    ) {
        Row(
            modifier =
                Modifier
                    .menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                        enabled = true,
                    ).height(44.dp)
                    .fillMaxWidth()
                    .background(AppDropdownDefaults.BackgroundColor, shape)
                    .border(1.dp, AppDropdownDefaults.BorderColor, shape)
                    .padding(start = 16.dp, top = 10.dp, end = 12.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = displayText,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = textStyle,
                color = textColor,
            )
            Spacer(Modifier.width(10.dp))
            Icon(
                painter = painterResource(R.drawable.ic_down_24),
                contentDescription = null,
                modifier = Modifier.rotate(if (expanded) 180f else 0f),
                tint = Color.Unspecified,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 0.dp, y = 4.dp),
            modifier =
                Modifier
                    .exposedDropdownSize()
                    .background(AppColor.Gray0, shape)
                    .border(1.dp, AppColor.Gray100, shape)
                    .padding(start = 16.dp, top = 4.dp, end = 16.dp),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
        ) {
            items.forEachIndexed { index, text ->

                AppDropdownItem(
                    Modifier.noRippleClickable {
                        onSelect(index)
                        expanded = false
                    },
                    text,
                )
            }
        }
    }
}

@Composable
private fun AppDropdownItem(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        text = text,
        style = AppTextStyle.Body01Regular,
        color = AppDropdownDefaults.TextColor,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
    )
}

private object AppDropdownDefaults {
    val Shape = RoundedCornerShape(8.dp)
    val BackgroundColor = AppColor.Gray0
    val BorderColor = AppColor.Gray100
    val TextColor = AppColor.TextPrimary
}

@Preview(showBackground = true, widthDp = 360, heightDp = 500)
/**
 * NOTE:
 * 이 프리뷰는 드롭다운 헤더/레이아웃 확인용
 * 실제 드롭다운 확장 동작은 에뮬레이터/실기기에서 Run으로 확인
 */
@Composable
fun PreviewAppDropdown_NonInteractive() {
    var selected by remember { mutableStateOf(-1) }

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .width(350.dp)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Top + WindowInsetsSides.Bottom,
                        ),
                    ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AppDropdown(
                items = listOf("서울", "부산", "제주", "대전", "지역", "지역지역"),
                selectedIndex = selected,
                onSelect = { selected = it },
                placeholder = "지역 선택",
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "selectedIndex: $selected",
            )
        }
    }
}
