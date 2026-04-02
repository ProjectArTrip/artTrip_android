package com.arttrip.android.presentation.home.ui.datefilter

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.component.calendar.DayChipCase03
import com.arttrip.android.core.ui.component.calendar.DayChipStateCase03
import com.arttrip.android.core.ui.component.calendar.WEEKDAY_LABELS
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle
import java.time.LocalDate
import java.time.YearMonth

private fun LocalDate.koreanDayOfWeek(): String = WEEKDAY_LABELS[dayOfWeek.value % 7]

private fun LocalDate.toFilterLabel(): String = "$monthValue.$dayOfMonth(${koreanDayOfWeek()})"

private fun LocalDate.chipState(
    today: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate?,
): DayChipStateCase03 = when {
    this < today -> DayChipStateCase03.Past
    this == startDate || this == endDate -> DayChipStateCase03.Selected
    this == today -> DayChipStateCase03.Today
    else -> DayChipStateCase03.Future
}


@Composable
fun DatePickerContent(onPickPreset: (String) -> Unit) {
    val today = remember { LocalDate.now() }
    val startMonth = remember { YearMonth.now() }
    var startDate by remember { mutableStateOf(today) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

    LaunchedEffect(startDate, endDate) {
        val end = endDate
        onPickPreset(
            if (end == null) "${startDate.toFilterLabel()} -"
            else "${startDate.toFilterLabel()} - ${end.toFilterLabel()}"
        )
    }

    fun onDayClick(date: LocalDate) {
        if (date < today) return
        when {
            endDate != null -> { startDate = date; endDate = null }
            date >= startDate -> endDate = date
            else -> { startDate = date; endDate = null }
        }
    }

    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        WeekdayHeader()
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(listState),
        ) {
            items(7) { monthOffset ->
                MonthSection(
                    yearMonth = startMonth.plusMonths(monthOffset.toLong()),
                    today = today,
                    startDate = startDate,
                    endDate = endDate,
                    onDayClick = ::onDayClick,
                )
            }
        }
    }
}


@Composable
private fun WeekdayHeader() {
    Column(modifier = Modifier.fillMaxWidth().background(AppColor.Gray0)) {
        Row(modifier = Modifier.fillMaxWidth().height(32.dp).padding(horizontal = 10.dp)) {
            WEEKDAY_LABELS.forEach { label ->
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = label,
                        style = AppTextStyle.Body01Regular,
                        color = AppColor.TextTertiary,
                    )
                }
            }
        }
        HorizontalDivider(color = AppColor.Gray100, thickness = 1.dp)
    }
}

@Composable
private fun MonthSection(
    yearMonth: YearMonth,
    today: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate?,
    onDayClick: (LocalDate) -> Unit,
) {
    val firstDay = remember(yearMonth) { yearMonth.atDay(1) }
    val totalDays = remember(yearMonth) { yearMonth.lengthOfMonth() }
    val startOffset = remember(firstDay) { firstDay.dayOfWeek.value % 7 }
    val weeks = remember(startOffset, totalDays) { (startOffset + totalDays + 6) / 7 }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = "${yearMonth.year}.${yearMonth.monthValue.toString().padStart(2, '0')}",
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            repeat(weeks) { weekIndex ->
                WeekRow(
                    yearMonth = yearMonth,
                    weekIndex = weekIndex,
                    startOffset = startOffset,
                    totalDays = totalDays,
                    today = today,
                    startDate = startDate,
                    endDate = endDate,
                    onDayClick = onDayClick,
                )
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun WeekRow(
    yearMonth: YearMonth,
    weekIndex: Int,
    startOffset: Int,
    totalDays: Int,
    today: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate?,
    onDayClick: (LocalDate) -> Unit,
) {
    val dates = remember(yearMonth, weekIndex, startOffset, totalDays) {
        List(7) { col ->
            val dom = weekIndex * 7 + col - startOffset + 1
            if (dom in 1..totalDays) yearMonth.atDay(dom) else null
        }
    }

    Box(modifier = Modifier.fillMaxWidth().height(36.dp)) {
        // 하이라이트 레이어 (padding 없이 full width)
        Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            dates.forEach { date ->
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    val end = endDate
                    if (date != null && end != null && startDate != end) {
                        RangeHighlight(date = date, startDate = startDate, endDate = end)
                    }
                }
            }
        }

        // 칩 레이어
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            dates.forEach { date ->
                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    if (date != null) {
                        DayChipCase03(
                            dayOfMonth = date.dayOfMonth,
                            state = date.chipState(today, startDate, endDate),
                            onClick = { onDayClick(date) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BoxScope.RangeHighlight(
    date: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val modifier = when {
        date == startDate ->
            Modifier
                .fillMaxWidth(0.5f)
                .height(24.dp)
                .background(AppColor.Primary100)
                .align(Alignment.CenterEnd)
        date == endDate ->
            Modifier
                .fillMaxWidth(0.5f)
                .height(24.dp)
                .background(AppColor.Primary100)
                .align(Alignment.CenterStart)
        date > startDate && date < endDate ->
            Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(AppColor.Primary100)
        else -> return
    }
    Box(modifier)
}
