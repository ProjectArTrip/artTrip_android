package com.arttrip.android.core.ui.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import com.arttrip.android.core.util.noRippleClickable
import java.time.DayOfWeek
import java.util.Locale
import java.time.format.TextStyle as JTextStyle

/**
 *  Figma의 **DayChip case01** 상태
 *
 * - [Unselected] : 기본 상태
 * - [Selected] : 선택 상태
 */
enum class DayChipStateCase01 {
    Unselected,
    Selected,
}

/**
 * Figma의 **DayChip case01** 컴포넌트
 *
 * 동그라미 안에 날짜, 아래에 요일(NARROW, ko-KR) 텍스트 표시
 *
 * @param dayOfMonth 표시할 일(day). 유효 범위는 1~31
 * @param dayOfWeek 해당 날짜의 요일.
 * @param modifier 외부에서 전달받는 [Modifier].
 * @param state [unselected][DayChipStateCase01.Unselected] / [selected][DayChipStateCase01.Selected] 스타일적용.
 * @param onClick 칩 클릭 시 호출되는 콜백.
 *
 * @throws IllegalArgumentException [dayOfMonth]가 1~31 범위를 벗어난 경우.
 */
@Composable
fun DayChipCase01(
    modifier: Modifier = Modifier,
    dayOfMonth: Int,
    dayOfWeek: DayOfWeek,
    state: DayChipStateCase01,
    onClick: () -> Unit = {},
) {
    require(dayOfMonth in 1..31) {
        "dayOfMonth must be in 1..31, but was $dayOfMonth"
    }

    val colors = DayChipDefaults.colors(state)
    val typography = DayChipDefaults.typography(state)
    val metrics = DayChipDefaults.metrics(state)

    val dayText = dayOfMonth.toString()
    val weekdayText = dayOfWeek.getDisplayName(JTextStyle.NARROW, Locale.KOREAN)

    Column(
        modifier =
            modifier
                .width(metrics.width)
                .height(metrics.height)
                .noRippleClickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier =
                Modifier
                    .size(metrics.circleSize)
                    .background(colors.circleColor, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = dayText,
                style = typography.dayTextStyle,
                color = colors.dayTextColor,
            )
        }

        Text(
            text = weekdayText,
            style = typography.weekdayTextStyle,
            color = colors.weekdayTextColor,
        )
    }
}

/**
 * Figma의 **DayChip case02** 상태.
 *
 * - [Disabled] : 비활성
 * - [Unselected] : 기본 상태
 * - [Selected] : 선택 상태
 * - [Included] : 범위 포함 등 특수 표시 상태
 */
enum class DayChipStateCase02 {
    Disabled,
    Unselected,
    Selected,
    Included,
}

/**
 * Figma의 **DayChip case02** 컴포넌트.
 *
 *  숫자만 표시되는 단일 라인 날짜 칩
 *
 * @param dayOfMonth 표시할 일(day). 유효 범위는 1~31.
 * @param modifier 외부에서 전달받는 [Modifier].
 * @param state [disabled][DayChipStateCase02.Disabled], [unselected][DayChipStateCase02.Unselected],[selected][DayChipStateCase02.Selected],[included][DayChipStateCase02.Included] 스타일 적용
 * @param onClick 칩 클릭 시 호출되는 콜백.
 *
 * @throws IllegalArgumentException [dayOfMonth]가 1~31 범위를 벗어난 경우.
 */
@Composable
fun DayChipCase02(
    modifier: Modifier = Modifier,
    dayOfMonth: Int,
    state: DayChipStateCase02,
    onClick: () -> Unit = {},
) {
    require(dayOfMonth in 1..31) {
        "dayOfMonth must be in 1..31, but was $dayOfMonth"
    }

    val colors = DayChipDefaults.colors(state)
    val typography = DayChipDefaults.typography(state)
    val metrics = DayChipDefaults.metrics(state)

    val dayText = dayOfMonth.toString()

    Box(
        modifier =
            modifier
                .width(metrics.width)
                .height(metrics.height)
                .background(colors.backgroundColor, shape = CircleShape)
                .noRippleClickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = dayText,
            style = typography.dayTextStyle,
            color = colors.dayTextColor,
        )
    }
}

// ============================================================
// Style models / tokens
// ============================================================

@Stable
internal data class DayChipCase01Colors(
    val circleColor: Color,
    val dayTextColor: Color,
    val weekdayTextColor: Color,
)

@Stable
internal data class DayChipCase01Typography(
    val dayTextStyle: TextStyle,
    val weekdayTextStyle: TextStyle,
)

@Stable
internal data class DayChipCase01Metrics(
    val width: Dp,
    val height: Dp,
    val circleSize: Dp,
)

@Stable
internal data class DayChipCase02Colors(
    val backgroundColor: Color,
    val dayTextColor: Color,
)

@Stable
internal data class DayChipCase02Typography(
    val dayTextStyle: TextStyle,
)

@Stable
internal data class DayChipCase02Metrics(
    val width: Dp,
    val height: Dp,
)

// ============================================================
// Case03
// ============================================================

/**
 * DayChip case03 상태
 *
 * - [Past] : 오늘 이전 — 회색 글자, 배경/테두리 없음
 * - [Today] : 오늘 — 연회색 배경, 회색 원형 테두리, 검정 글자
 * - [Future] : 이후 날짜 — 검정 글자, 배경/테두리 없음
 * - [Selected] : 선택됨 — 보라색 배경, 흰색 글자, 테두리 없음
 */
enum class DayChipStateCase03 {
    Past,
    Today,
    Future,
    Selected,
}

/**
 * DayChip case03 컴포넌트
 *
 * 날짜 바텀시트 전용 컴포넌트.
 *
 * @param dayOfMonth 표시할 일(day). 유효 범위는 1~31.
 */
@Composable
fun DayChipCase03(
    modifier: Modifier = Modifier,
    dayOfMonth: Int,
    state: DayChipStateCase03,
    onClick: () -> Unit = {},
) {
    require(dayOfMonth in 1..31) {
        "dayOfMonth must be in 1..31, but was $dayOfMonth"
    }

    val colors = DayChipDefaults.colors(state)
    val metrics = DayChipDefaults.metrics(state)

    Box(
        modifier =
            modifier
                .size(metrics.size)
                .background(colors.backgroundColor, shape = CircleShape)
                .then(
                    if (colors.borderColor != Color.Transparent) {
                        Modifier.border(1.dp, colors.borderColor, CircleShape)
                    } else {
                        Modifier
                    },
                ).noRippleClickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = dayOfMonth.toString(),
            style = AppTextStyle.Body01Bold,
            color = colors.dayTextColor,
        )
    }
}

@Stable
internal data class DayChipCase03Colors(
    val backgroundColor: Color,
    val borderColor: Color,
    val dayTextColor: Color,
)

@Stable
internal data class DayChipCase03Metrics(
    val size: Dp,
)

internal object DayChipDefaults {
    @Composable
    fun colors(state: DayChipStateCase01): DayChipCase01Colors =
        when (state) {
            DayChipStateCase01.Unselected ->
                DayChipCase01Colors(
                    circleColor = AppColor.Gray0,
                    dayTextColor = AppColor.TextPrimary,
                    weekdayTextColor = AppColor.TextPrimary,
                )
            DayChipStateCase01.Selected ->
                DayChipCase01Colors(
                    circleColor = AppColor.Primary300,
                    dayTextColor = AppColor.Gray0,
                    weekdayTextColor = AppColor.TextPoint,
                )
        }

    @Composable
    fun colors(state: DayChipStateCase02): DayChipCase02Colors =
        when (state) {
            DayChipStateCase02.Disabled ->
                DayChipCase02Colors(
                    backgroundColor = Color.Transparent,
                    dayTextColor = AppColor.TextTertiary,
                )
            DayChipStateCase02.Unselected ->
                DayChipCase02Colors(
                    backgroundColor = Color.Transparent,
                    dayTextColor = AppColor.TextPrimary,
                )
            DayChipStateCase02.Selected ->
                DayChipCase02Colors(
                    backgroundColor = AppColor.Primary300,
                    dayTextColor = AppColor.TextWhite,
                )
            DayChipStateCase02.Included ->
                DayChipCase02Colors(
                    backgroundColor = Color.Transparent,
                    dayTextColor = AppColor.TextPoint,
                )
        }

    @Composable
    fun typography(state: DayChipStateCase01): DayChipCase01Typography =
        when (state) {
            DayChipStateCase01.Unselected ->
                DayChipCase01Typography(
                    dayTextStyle = AppTextStyle.Body01Regular,
                    weekdayTextStyle = AppTextStyle.Body01Light,
                )
            DayChipStateCase01.Selected ->
                DayChipCase01Typography(
                    dayTextStyle = AppTextStyle.Body01Bold,
                    weekdayTextStyle = AppTextStyle.Body01Bold,
                )
        }

    @Composable
    fun typography(state: DayChipStateCase02): DayChipCase02Typography =
        when (state) {
            DayChipStateCase02.Disabled,
            DayChipStateCase02.Unselected,
            DayChipStateCase02.Selected,
            DayChipStateCase02.Included,
            ->
                DayChipCase02Typography(
                    dayTextStyle = AppTextStyle.Body01Bold,
                )
        }

    @Composable
    fun metrics(state: DayChipStateCase01): DayChipCase01Metrics =
        DayChipCase01Metrics(
            width = 30.dp,
            height = 50.dp,
            circleSize = 28.dp,
        )

    @Composable
    fun metrics(state: DayChipStateCase02): DayChipCase02Metrics =
        DayChipCase02Metrics(
            width = 28.dp,
            height = 24.dp,
        )

    @Composable
    fun colors(state: DayChipStateCase03): DayChipCase03Colors =
        when (state) {
            DayChipStateCase03.Past ->
                DayChipCase03Colors(
                    backgroundColor = Color.Transparent,
                    borderColor = Color.Transparent,
                    dayTextColor = AppColor.TextTertiary,
                )
            DayChipStateCase03.Today ->
                DayChipCase03Colors(
                    backgroundColor = AppColor.SubLightGray,
                    borderColor = AppColor.Gray100,
                    dayTextColor = AppColor.TextSecondary,
                )
            DayChipStateCase03.Future ->
                DayChipCase03Colors(
                    backgroundColor = Color.Transparent,
                    borderColor = Color.Transparent,
                    dayTextColor = AppColor.TextPrimary,
                )
            DayChipStateCase03.Selected ->
                DayChipCase03Colors(
                    backgroundColor = AppColor.Primary300,
                    borderColor = Color.Transparent,
                    dayTextColor = AppColor.TextWhite,
                )
        }

    @Composable
    fun metrics(state: DayChipStateCase03): DayChipCase03Metrics = DayChipCase03Metrics(size = 28.dp)
}

@Preview
@Composable
fun SampleDayChip_Case01() {
    var state by remember { mutableStateOf(DayChipStateCase01.Selected) }
    DayChipCase01(
        dayOfMonth = 3,
        dayOfWeek = DayOfWeek.MONDAY,
        state = state,
        onClick = {
            state =
                when (state) {
                    DayChipStateCase01.Unselected -> DayChipStateCase01.Selected
                    DayChipStateCase01.Selected -> DayChipStateCase01.Unselected
                }
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun SampleDayChip_Case03_All() {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DayChipCase03(dayOfMonth = 10, state = DayChipStateCase03.Past)
        DayChipCase03(dayOfMonth = 11, state = DayChipStateCase03.Today)
        DayChipCase03(dayOfMonth = 12, state = DayChipStateCase03.Future)
        DayChipCase03(dayOfMonth = 13, state = DayChipStateCase03.Selected)
    }
}

@Preview(showBackground = true)
@Composable
fun SampleDayChip_Case03() {
    var state by remember { mutableStateOf(DayChipStateCase03.Past) }
    DayChipCase03(
        dayOfMonth = 15,
        state = state,
        onClick = {
            state =
                when (state) {
                    DayChipStateCase03.Past -> DayChipStateCase03.Today
                    DayChipStateCase03.Today -> DayChipStateCase03.Future
                    DayChipStateCase03.Future -> DayChipStateCase03.Selected
                    DayChipStateCase03.Selected -> DayChipStateCase03.Past
                }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun SampleDayChip_Case02() {
    var state by remember { mutableStateOf(DayChipStateCase02.Disabled) }
    DayChipCase02(
        dayOfMonth = 9,
        state = state,
        onClick = {
            state =
                when (state) {
                    DayChipStateCase02.Disabled -> DayChipStateCase02.Unselected
                    DayChipStateCase02.Unselected -> DayChipStateCase02.Selected
                    DayChipStateCase02.Selected -> DayChipStateCase02.Included
                    DayChipStateCase02.Included -> DayChipStateCase02.Disabled
                }
        },
    )
}
