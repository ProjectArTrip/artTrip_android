package com.arttrip.android.presentation.home.sub.dateresult

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun DateResultScreen(innerPadding: PaddingValues) {
    Box(
        modifier =
            androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(innerPadding),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "날짜 필터 화면 (예시 텍스트)")
    }
}
