package com.arttrip.app.presentation.home.ui.feedback

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arttrip.app.R
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle

@Composable
fun ErrorExhibitionList() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            modifier =
                Modifier
                    .height(62.dp),
        )
        Icon(
            painter = painterResource(R.drawable.ic_home_error_96),
            contentDescription = "error",
            tint = Color.Unspecified,
        )
        Spacer(
            modifier =
                Modifier
                    .height(4.dp),
        )
        Text(
            text = "일시적인 오류가 발생했어요",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
        )
        Spacer(
            modifier =
                Modifier
                    .height(8.dp),
        )
        Text(
            text =
                "이용에 불편을 드려 죄송합니다\n" +
                    "잠시 후에 다시 시도해 주세요",
            style = AppTextStyle.Body02Regular,
            color = AppColor.TextTertiary,
            textAlign = TextAlign.Center,
        )
    }
}
