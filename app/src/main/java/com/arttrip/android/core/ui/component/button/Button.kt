package com.arttrip.android.core.ui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.core.ui.theme.AppTextStyle

/**
 *### Figma: btn(default/disabled)
 *  - `default` == `enabled`
 *  - `disabled` == `!enabled`
 */
@Composable
fun AppButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    text: String,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier =
            modifier
                .fillMaxWidth()
                .height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = AppColor.Primary300,
                contentColor = AppColor.TextWhite,
                disabledContainerColor = AppColor.Gray100,
                disabledContentColor = AppColor.TextTertiary,
            ),
        contentPadding =
            PaddingValues(
                top = 9.dp,
                bottom = 9.dp,
            ),
    ) {
        Text(
            text = text,
            style = AppTextStyle.Title02Bold,
        )
    }
}

/**
 *### Figma: btn_review
 */
@Composable
fun ReviewButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        onClick = onClick,
        modifier = modifier.height(34.dp),
        shape = RoundedCornerShape(4.dp),
        color = AppColor.Gray0,
        contentColor = AppColor.TextPrimary,
        border = BorderStroke(1.dp, AppColor.Gray50),
        interactionSource = interactionSource,
    ) {
        Row(
            Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text, style = AppTextStyle.Body02Bold)
        }
    }
}

@Preview(
    name = "All Buttons Preview",
    showBackground = false,
    widthDp = 360,
)
@Composable
private fun PreviewAllButtons_Interactive() {
    var appBtnEnabled by remember { mutableStateOf(true) }

    Surface(
        color = Color.Black,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AppButton(
                text = if (appBtnEnabled) "Btn (Enabled)" else "Btn (Disabled)",
                enabled = appBtnEnabled,
                onClick = { appBtnEnabled = !appBtnEnabled },
            )

            ReviewButton(
                text = "Btn",
                onClick = { },
            )
        }
    }
}
