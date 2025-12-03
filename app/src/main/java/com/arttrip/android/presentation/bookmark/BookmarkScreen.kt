package com.arttrip.android.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
) {
    Column(
        modifier = modifier.padding(innerPadding),
    ) {
        Text(
            text = "Bookmark",
        )
    }
}
