package com.arttrip.android.presentation.home.ui.datefilter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.component.calendar.WEEKDAY_LABELS
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle

@Composable
fun DatePickerContent(onPickPreset: (String) -> Unit) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(390.dp)
                .background(
                    color = AppColor.Gray50,
                    shape = RoundedCornerShape(8.dp),
                ),
    )
}

@Composable
private fun WeekdayRow(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().height(40.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        WEEKDAY_LABELS.forEach { label ->
            Text(
                text = label,
                style = AppTextStyle.Body01Regular,
                color = AppColor.TextPrimary,
            )
        }
    }
}
