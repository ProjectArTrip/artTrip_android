package com.arttrip.android.core.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arttrip.android.core.ui.component.button.AppButton
import com.arttrip.android.core.ui.component.button.AppButtonVariant
import com.arttrip.android.core.ui.theme.AppColor

@Composable
fun AppDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    title: @Composable (() -> Unit)? = null,
    message: @Composable (() -> Unit)? = null,
    content: @Composable (ColumnScope.() -> Unit)? = null,
    primaryText: String,
    onPrimaryClick: () -> Unit,
    secondaryText: String,
    onSecondaryClick: () -> Unit,
    primaryEnabled: Boolean = true,
    secondaryEnabled: Boolean = true,
    scrimColor: Color = AppColor.Gray900.copy(alpha = 0.6f),
    containerColor: Color = AppColor.Gray0,
    shape: Shape = RoundedCornerShape(16.dp),
    outerPadding: Dp = 32.dp,
    innerPadding: Dp = 18.dp,
) {
    if (!visible) return

    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(scrimColor),
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = outerPadding),
                shape = shape,
                color = containerColor,
                shadowElevation = 12.dp,
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    // TODO

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        AppButton(
                            modifier = Modifier.fillMaxWidth(),
                            variant = AppButtonVariant.Secondary,
                            text = secondaryText,
                            onClick = { onSecondaryClick() },
                            enabled = secondaryEnabled,
                        )

                        AppButton(
                            modifier = Modifier.fillMaxWidth(),
                            variant = AppButtonVariant.Primary,
                            text = primaryText,
                            onClick = { onPrimaryClick() },
                            enabled = primaryEnabled,
                        )
                    }
                }
            }
        }
    }
}
