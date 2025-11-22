package com.arttrip.android.core.ui.component.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.ui.theme.ArtTripTheme

// ============================================================
// Public API
// ============================================================

/**
 * Figma:
 *
 * - [Tab case01][AppTabCase.Case01]: 2 tabs
 * - [Tab case02][AppTabCase.Case02]: 2 tabs
 * - [Tab case03][AppTabCase.Case03]: 2 tabs (between-gap + text-width indicator)
 * - [Tab case04][AppTabCase.Case04]: 3 tabs
 */
enum class AppTabCase {
    Case01, // tab case 01 (2탭, 기본 스타일)
    Case02, // tab case 02 (2탭, 01과 스타일 동일)
    Case03, // tab case 03 (2탭, 커스텀 버튼형 탭)
    Case04, // tab case 04 (3탭)
}

/**
 * Figma의 **Tab case 01~04** 컴포넌트
 *
 * - Case 01 / 02 / 04는 M3 SecondaryTabRow 기반
 * - Case 03은 Row + 커스텀 버튼(TabItem) 기반
 *
 * @param case Figma 탭 스타일 케이스 (tab case 01~04)
 * @param tabs 탭 라벨 리스트
 *   - Case 01, 02, 03 → size = 2
 *   - Case 04 → size = 3
 * @param selectedIndex 현재 선택된 탭 인덱스
 * @param onTabSelected 탭 선택 콜백
 * @param modifier 외부에서 전달받는 [Modifier].
 *
 * @throws IllegalArgumentException `tabs.size`가 2나 3이상이 아닌 경우
 */
@Composable
fun AppTabRow(
    case: AppTabCase,
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors: AppTabRowColors = AppTabRowDefaults.colors()
    val typography: AppTabRowTypography = AppTabRowDefaults.typography(case)
    val metrics: AppTabRowMetrics = AppTabRowDefaults.metrics(case)

    AppTabRowImpl(
        case = case,
        tabs = tabs,
        selectedIndex = selectedIndex,
        onTabSelected = onTabSelected,
        modifier = modifier,
        colors = colors,
        typography = typography,
        metrics = metrics,
    )
}

// ============================================================
// Internal implementation
// ============================================================

@Composable
internal fun AppTabRowImpl(
    case: AppTabCase,
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    colors: AppTabRowColors,
    typography: AppTabRowTypography,
    metrics: AppTabRowMetrics,
) {
    when (case) {
        AppTabCase.Case01,
        AppTabCase.Case02,
        AppTabCase.Case03,
        ->
            require(tabs.size >= 2) {
                "AppTabRow($case)는 최소 2개 이상의 탭이 필요합니다. tabs.size=${tabs.size}"
            }
        AppTabCase.Case04 ->
            require(tabs.size >= 3) {
                "AppTabRow($case)는 최소 3개 이상의 탭이 필요합니다. tabs.size=${tabs.size}"
            }
    }

    when (case) {
        AppTabCase.Case03 -> {
            Case03TabRow(
                titles = tabs,
                selectedIndex = selectedIndex,
                onTabSelected = onTabSelected,
                colors = colors,
                typography = typography,
                metrics = metrics,
                modifier = modifier,
            )
        }

        else -> {
            DefaultTabRow(
                tabs = tabs,
                selectedIndex = selectedIndex,
                onTabSelected = onTabSelected,
                colors = colors,
                typography = typography,
                metrics = metrics,
                modifier = modifier,
            )
        }
    }
}

// ------------------------------------------------------------
// Default TabRow (Case 01 / 02 / 04)
// ------------------------------------------------------------

@Composable
private fun DefaultTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    colors: AppTabRowColors,
    typography: AppTabRowTypography,
    metrics: AppTabRowMetrics,
    modifier: Modifier = Modifier,
) {
    SecondaryTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier.fillMaxWidth(),
        containerColor = Color.Transparent,
        contentColor = colors.selectedTextColor,
        indicator = {
            DefaultTabIndicator(
                selectedIndex = selectedIndex,
                colors = colors,
                metrics = metrics,
            )
        },
        divider = {
            if (metrics.dividerHeight > 0.dp) {
                HorizontalDivider(
                    color = colors.dividerColor,
                    thickness = metrics.dividerHeight,
                )
            }
        },
    ) {
        tabs.forEachIndexed { index, title ->
            val selected = index == selectedIndex
            val textStyle =
                if (selected) typography.selectedTextStyle else typography.unselectedTextStyle
            val textColor =
                if (selected) colors.selectedTextColor else colors.unselectedTextColor
            val tabModifier = Modifier.padding(metrics.contentPadding)

            DefaultAppTab(
                title = title,
                modifier = tabModifier,
                textStyle = textStyle,
                textColor = textColor,
                onClick = { onTabSelected(index) },
            )
        }
    }
}

@Composable
private fun TabIndicatorScope.DefaultTabIndicator(
    selectedIndex: Int,
    colors: AppTabRowColors,
    metrics: AppTabRowMetrics,
) {
    TabRowDefaults.SecondaryIndicator(
        modifier = Modifier.tabIndicatorOffset(selectedIndex),
        height = metrics.indicatorHeight,
        color = colors.indicatorColor,
    )
}

@Composable
private fun DefaultAppTab(
    title: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    textColor: Color,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier =
            modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = title,
            style = textStyle,
            color = textColor,
            textAlign = TextAlign.Center,
        )
    }
}

// ------------------------------------------------------------
// Case03: 커스텀 2탭 버튼 탭
// ------------------------------------------------------------

private val CASE03_TAB_SPACING = 24.dp

@Composable
private fun Case03TabRow(
    titles: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    colors: AppTabRowColors,
    typography: AppTabRowTypography,
    metrics: AppTabRowMetrics,
    modifier: Modifier = Modifier,
) {
    require(titles.size == 2) { "Case03TabRow는 2탭 전용입니다." }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(CASE03_TAB_SPACING),
    ) {
        titles.forEachIndexed { index, title ->
            val selected = index == selectedIndex

            Case03Tab(
                text = title,
                selected = selected,
                colors = colors,
                typography = typography,
                metrics = metrics,
                onClick = {
                    if (!selected) {
                        onTabSelected(index)
                    }
                },
            )
        }
    }
}

@Composable
private fun Case03Tab(
    text: String,
    selected: Boolean,
    colors: AppTabRowColors,
    typography: AppTabRowTypography,
    metrics: AppTabRowMetrics,
    onClick: () -> Unit,
) {
    val textStyle =
        if (selected) typography.selectedTextStyle else typography.unselectedTextStyle
    val textColor =
        if (selected) colors.selectedTextColor else colors.unselectedTextColor
    val indicatorColor =
        if (selected) colors.indicatorColor else colors.dividerColor
    val indicatorHeight =
        if (selected) metrics.indicatorHeight else metrics.dividerHeight

    Column(
        modifier =
            Modifier
                .width(IntrinsicSize.Max)
                .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(metrics.contentPadding),
            style = textStyle,
            color = textColor,
            textAlign = TextAlign.Center,
        )

        if (indicatorHeight > 0.dp) {
            Spacer(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(indicatorHeight)
                        .background(indicatorColor),
            )
        }
    }
}

// ============================================================
// Style models / tokens
// ============================================================

@Stable
internal data class AppTabRowColors(
    val selectedTextColor: Color,
    val unselectedTextColor: Color,
    val indicatorColor: Color,
    val dividerColor: Color,
)

@Stable
internal data class AppTabRowTypography(
    val selectedTextStyle: TextStyle,
    val unselectedTextStyle: TextStyle,
)

@Stable
internal data class AppTabRowMetrics(
    val indicatorHeight: Dp,
    val dividerHeight: Dp,
    val contentPadding: PaddingValues,
)

/**
 * AppTabRow용 Defaults.
 *
 */
internal object AppTabRowDefaults {
    @Composable
    fun colors(): AppTabRowColors =
        AppTabRowColors(
            selectedTextColor = AppColor.TextPoint,
            unselectedTextColor = AppColor.TextTertiary,
            indicatorColor = AppColor.Primary200,
            dividerColor = AppColor.Gray100,
        )

    @Composable
    fun typography(case: AppTabCase): AppTabRowTypography =
        when (case) {
            AppTabCase.Case03 ->
                AppTabRowTypography(
                    selectedTextStyle = AppTextStyle.Title01Bold,
                    unselectedTextStyle = AppTextStyle.Title01Bold,
                )

            AppTabCase.Case01,
            AppTabCase.Case02,
            AppTabCase.Case04,
            ->
                AppTabRowTypography(
                    selectedTextStyle = AppTextStyle.Title02Bold,
                    unselectedTextStyle = AppTextStyle.Title02Light,
                )
        }

    @Composable
    fun metrics(case: AppTabCase): AppTabRowMetrics =
        when (case) {
            AppTabCase.Case01 ->
                AppTabRowMetrics(
                    indicatorHeight = 2.dp,
                    dividerHeight = 1.dp,
                    contentPadding = PaddingValues(top = 16.dp, bottom = 8.dp),
                )

            AppTabCase.Case02 ->
                AppTabRowMetrics(
                    indicatorHeight = 2.dp,
                    dividerHeight = 1.dp,
                    contentPadding = PaddingValues(top = 16.dp, bottom = 8.dp),
                )

            AppTabCase.Case03 ->
                AppTabRowMetrics(
                    indicatorHeight = 2.dp,
                    dividerHeight = 0.dp,
                    contentPadding = PaddingValues(top = 4.dp, bottom = 3.dp),
                )

            AppTabCase.Case04 ->
                AppTabRowMetrics(
                    indicatorHeight = 2.dp,
                    dividerHeight = 1.dp,
                    contentPadding = PaddingValues(bottom = 7.dp),
                )
        }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Preview_AppTabRow_AllCases() {
    ArtTripTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                // Case01
                var c1 by remember { mutableIntStateOf(0) }
                AppTabRow(
                    case = AppTabCase.Case01,
                    tabs = listOf("Case1", "Case1"),
                    selectedIndex = c1,
                    onTabSelected = { c1 = it },
                )

                // Case02
                var c2 by remember { mutableIntStateOf(0) }
                AppTabRow(
                    case = AppTabCase.Case02,
                    tabs = listOf("Case2", "Case2"),
                    selectedIndex = c2,
                    onTabSelected = { c2 = it },
                )

                // Case03
                var c3 by remember { mutableIntStateOf(0) }
                AppTabRow(
                    case = AppTabCase.Case03,
                    tabs = listOf("Case3", "Case3"),
                    selectedIndex = c3,
                    onTabSelected = { c3 = it },
                )

                // Case04
                var c4 by remember { mutableIntStateOf(0) }
                AppTabRow(
                    case = AppTabCase.Case04,
                    tabs = listOf("Case4", "Case4", "Case4"),
                    selectedIndex = c4,
                    onTabSelected = { c4 = it },
                )
            }
        }
    }
}