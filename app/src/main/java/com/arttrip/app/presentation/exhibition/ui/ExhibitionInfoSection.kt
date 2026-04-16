package com.arttrip.app.presentation.exhibition.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arttrip.app.core.ui.component.button.AppButton
import com.arttrip.app.core.ui.theme.AppColor
import com.arttrip.app.core.ui.theme.AppTextStyle
import com.arttrip.app.domain.model.exhibition.ExhibitionDetail

@Composable
fun ExhibitionInfoSection(
    modifier: Modifier = Modifier,
    detail: ExhibitionDetail,
) {
    val context = LocalContext.current
    val url = detail.ticketUrl?.trim().orEmpty()
    val enabled = url.isNotBlank()

    Column(
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        Text(
            text = detail.title,
            style = AppTextStyle.Headline,
            color = AppColor.TextPrimary,
            textAlign = TextAlign.Start,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = detail.hallName.orEmpty(),
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
            textAlign = TextAlign.Start,
        )
        Text(
            text = detail.exhibitPeriod.toDisplayPeriod(),
            style = AppTextStyle.Body01Regular,
            color = AppColor.TextPrimary,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
            enabled = enabled,
            text = "홈페이지 바로가기",
        )
    }
}

private fun String.toDisplayPeriod(): String {
    val parts = this.split("~").map { it.trim() }
    if (parts.size != 2) return this

    val start = parts[0].replace("-", ".")
    val end = parts[1].replace("-", ".")
    return "$start - $end"
}
