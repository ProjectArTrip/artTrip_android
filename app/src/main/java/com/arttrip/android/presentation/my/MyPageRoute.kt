package com.arttrip.android.presentation.my

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

@Composable
fun MyPageRoute(
    innerPadding: PaddingValues,
    onNavigateExhibitionDetail: (Int) -> Unit,
) {
    MyPageScreen(
        innerPadding = innerPadding,
        onNavigateExhibitionDetail = onNavigateExhibitionDetail,
    )
}
