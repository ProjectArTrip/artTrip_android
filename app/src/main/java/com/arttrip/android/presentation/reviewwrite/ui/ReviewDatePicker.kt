package com.arttrip.android.presentation.reviewwrite.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.component.calendar.DayChipCase02
import com.arttrip.android.core.ui.component.calendar.DayChipStateCase02
import com.arttrip.android.core.ui.component.calendar.WEEKDAY_LABELS
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import java.time.LocalDate
import java.time.YearMonth

/**
 * 단일 선택 DatePicker (월 이동 + 요일 라인 + 7열 그리드)
 *
 * - 첫/마지막 주는 인접 월 날짜를 함께 표시
 * - 날짜는 1개만 선택 가능
 * - 인접 월 날짜 클릭 시 해당 월로 이동만 하고 선택되지는 않음
 * - 오늘 포함 과거 날짜만 선택 가능
 * - 미래 날짜는 비활성 스타일로 표시
 */
@Composable
fun SingleSelectDatePicker(
    modifier: Modifier = Modifier,
    initialMonth: YearMonth = YearMonth.now(),
    initialSelected: LocalDate? = null,
    onMonthChanged: (YearMonth) -> Unit = {},
    onDateSelected: (LocalDate) -> Unit = {},
    onCloseClicked: () -> Unit,
) {
    var month by remember { mutableStateOf(initialMonth) }
    var selected by remember { mutableStateOf(initialSelected) }

    val today = LocalDate.now()
    val currentMonth = YearMonth.from(today)
    val canGoNextMonth = month < currentMonth

    val cells = remember(month) { buildMonthCellsWithAdjacent(month) }

    Column(modifier) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                CalendarHeader(
                    year = month.year,
                    month = month.monthValue,
                    canGoNext = canGoNextMonth,
                    onPrev = {
                        val newMonth = month.minusMonths(1)
                        month = newMonth
                        onMonthChanged(newMonth)
                    },
                    onNext = {
                        if (!canGoNextMonth) return@CalendarHeader
                        val newMonth = month.plusMonths(1)
                        month = newMonth
                        onMonthChanged(newMonth)
                    },
                )
            }
            AppIconButton(
                iconResId = R.drawable.ic_close_24,
                onIconClick = onCloseClicked,
            )
        }

        Spacer(Modifier.height(16.dp))

        WeekdayRow(modifier = Modifier.padding(horizontal = 20.dp))

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            cells.chunked(7).forEach { week ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    week.forEach { cell ->
                        val isFutureDate = cell.date.isAfter(today)
                        val state =
                            resolveDayState(
                                date = cell.date,
                                selected = selected,
                                inMonth = cell.inMonth,
                                today = today,
                            )
                        DayChipCase02(
                            dayOfMonth = cell.date.dayOfMonth,
                            state = state,
                            onClick = {
                                if (isFutureDate) return@DayChipCase02

                                if (!cell.inMonth) {
                                    val newMonth = YearMonth.from(cell.date)
                                    month = newMonth
                                    onMonthChanged(newMonth)
                                    return@DayChipCase02
                                }

                                selected = cell.date
                                onDateSelected(cell.date)
                            },
                        )
                    }
                }
            }
        }
    }
}

/* --------------------------------------------
 * UI Components
 * -------------------------------------------- */

@Composable
private fun CalendarHeader(
    year: Int,
    month: Int,
    canGoNext: Boolean,
    onPrev: () -> Unit,
    onNext: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        AppIconButton(
            iconResId = R.drawable.ic_back_24,
            onIconClick = onPrev,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "%04d %02d".format(year, month),
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(modifier = Modifier.width(4.dp))
        AppIconButton(
            iconResId = R.drawable.ic_more_24,
            enabled = canGoNext,
            onIconClick = onNext,
            tint = if (canGoNext) AppColor.Gray900 else AppColor.Gray100,
        )
    }
}

@Composable
private fun WeekdayRow(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        WEEKDAY_LABELS.forEach { label ->
            Text(
                text = label,
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPrimary,
            )
        }
    }
}

/* --------------------------------------------
 * Model + Builder
 * -------------------------------------------- */

private data class CalendarCell(
    val date: LocalDate,
    val inMonth: Boolean, // 이번 달이면 true, 이전/다음 달이면 false
)

/**
 * month의 달력을 7열로 만들되,
 * 첫 주는 이전 달 날짜로 채우고 마지막 주는 다음 달 날짜로 채움.
 */
private fun buildMonthCellsWithAdjacent(month: YearMonth): List<CalendarCell> {
    val first = month.atDay(1)
    val startIndex = first.dayOfWeek.value % 7 // 일=0 ... 토=6
    val lastDay = month.lengthOfMonth()

    val prevMonth = month.minusMonths(1)
    val nextMonth = month.plusMonths(1)

    val cells = mutableListOf<CalendarCell>()

    // 1) 앞쪽(이전 달) 채우기
    val prevLastDay = prevMonth.lengthOfMonth()
    for (i in startIndex downTo 1) {
        val day = prevLastDay - i + 1
        cells.add(CalendarCell(prevMonth.atDay(day), inMonth = false))
    }

    // 2) 이번 달 날짜 채우기
    for (d in 1..lastDay) {
        cells.add(CalendarCell(month.atDay(d), inMonth = true))
    }

    // 3) 뒤쪽(다음 달) 채우기 (7의 배수로 맞추기)
    var nextDay = 1
    while (cells.size % 7 != 0) {
        cells.add(CalendarCell(nextMonth.atDay(nextDay), inMonth = false))
        nextDay++
    }

    return cells
}

private fun resolveDayState(
    date: LocalDate,
    selected: LocalDate?,
    inMonth: Boolean,
    today: LocalDate,
): DayChipStateCase02 =
    when {
        !inMonth -> DayChipStateCase02.Disabled
        date.isAfter(today) -> DayChipStateCase02.Disabled
        selected == date -> DayChipStateCase02.Selected
        else -> DayChipStateCase02.Unselected
    }
