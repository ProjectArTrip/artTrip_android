package com.arttrip.android.presentation.exhibition.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.component.button.AppButton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle

@Composable
fun ExhibitionInfoSection(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        Text(
            text = "메이지·다이쇼 시대 예술의 장식적 취향을 통해 본 아르누보와 그 주변 환경",
            style = AppTextStyle.Headline,
            color = AppColor.TextPrimary,
            textAlign = TextAlign.Start,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "다케히사 유메지 미술관",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
            textAlign = TextAlign.Start,
        )
        Text(
            text = "2025.06.07 - 2025.09.14",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(
            onClick = {},
            enabled = true,
            text = "홈페이지 바로가기",
        )
    }
}
