package com.arttrip.app.presentation.exhibition.ui.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle

@Composable
fun ExhibitionMapTab() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(220.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AppColor.Gray50),
    ) {
        Text(
            text = "지도 탭 컨텐츠 영역 (임시)",
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
        )
    }
}
