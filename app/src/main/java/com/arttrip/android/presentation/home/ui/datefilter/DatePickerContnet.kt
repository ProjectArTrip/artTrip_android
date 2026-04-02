package com.arttrip.android.presentation.home.ui.datefilter

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun DatePickerContent(onPickPreset: (String) -> Unit) {
    val today = remember { LocalDate.now() }
    val startMonth = remember { YearMonth.now() }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val listState = rememberLazyListState()
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().background(AppColor.Gray0)) {
            WeekdayRow(modifier = Modifier.padding(horizontal = 10.dp))
            HorizontalDivider(color = AppColor.Gray100, thickness = 1.dp)
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            flingBehavior = snapFlingBehavior,
        ) {
            items(7) { monthOffset ->
                val yearMonth = startMonth.plusMonths(monthOffset.toLong())
                MonthSection(
                    yearMonth = yearMonth,
                    today = today,
                    selectedDate = selectedDate,
                    onDayClick = { date -> selectedDate = date },
                )
            }
        }
    }
}

@Composable
private fun WeekdayRow(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().height(32.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        WEEKDAY_LABELS.forEach { label ->
            Text(
                modifier =
                    Modifier
                        .size(28.dp)
                        .wrapContentSize(Alignment.Center),
                text = label,
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextTertiary,
            )
        }
    }
}

@Composable
private fun MonthSection(
    yearMonth: YearMonth,
    today: LocalDate,
    selectedDate: LocalDate?,
    onDayClick: (LocalDate) -> Unit,
) {
    val firstDayOfMonth = yearMonth.atDay(1)
    val totalDays = yearMonth.lengthOfMonth()
    // 일(0)~토(6) 기준 첫째 날의 열 인덱스
    val startOffset = firstDayOfMonth.dayOfWeek.value % 7
    val weeks = (startOffset + totalDays + 6) / 7

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = "${yearMonth.year}.${yearMonth.monthValue.toString().padStart(2, '0')}",
            style = AppTextStyle.Title02Bold,
            color = AppColor.TextPrimary,
        )
        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            repeat(weeks) { weekIndex ->
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(36.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    repeat(7) { dayOfWeekIndex ->
                        val cellIndex = weekIndex * 7 + dayOfWeekIndex
                        val dayOfMonth = cellIndex - startOffset + 1

                        if (dayOfMonth in 1..totalDays) {
                            val date = yearMonth.atDay(dayOfMonth)
                            val state =
                                when {
                                    date == selectedDate -> DayChipStateCase03.Selected
                                    date < today -> DayChipStateCase03.Past
                                    date == today -> DayChipStateCase03.Today
                                    else -> DayChipStateCase03.Future
                                }
                            DayChipCase03(
                                dayOfMonth = dayOfMonth,
                                state = state,
                                onClick = { onDayClick(date) },
                            )
                        } else {
                            Spacer(Modifier.size(28.dp))
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}
