package com.arttrip.android.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.android.core.ui.component.button.AppButton
import com.arttrip.android.core.ui.component.button.ReviewButton
import com.arttrip.android.core.ui.component.button.UploadButton

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(top = 100.dp),
    ) {
        var appBtnEnabled by remember { mutableStateOf(true) }

        // 1) AppButton: 클릭하면 enabled/disabled 토글
        AppButton(
            text = if (appBtnEnabled) "Btn (Enabled)" else "Btn (Disabled)",
            enabled = appBtnEnabled,
            onClick = { appBtnEnabled = !appBtnEnabled },
        )

        // 2) ReviewButton
        ReviewButton(
            text = "Btn",
            onClick = { },
        )

        // 3) UploadButton
        UploadButton(
            onClick = { },
        )
    }
}
