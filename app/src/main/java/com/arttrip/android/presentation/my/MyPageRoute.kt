package com.arttrip.android.presentation.my

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

@Composable
fun MyPageRoute(
    innerPadding: PaddingValues,
    onNavigate: (String) -> Unit,
) {
    MyPageScreen(
        innerPadding = innerPadding,
        onNavigate = onNavigate,
    )
}
