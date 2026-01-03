package com.arttrip.android.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arttrip.android.presentation.bookmark.contract.BookmarkIntent
import com.arttrip.android.presentation.bookmark.contract.BookmarkState

@Composable
fun BookmarkScreen(
    innerPadding: PaddingValues,
    state: BookmarkState,
    onIntent: (BookmarkIntent) -> Unit,
) {
    Column(
        modifier = Modifier.padding(innerPadding),
    ) {
        Text(
            text = "Bookmark",
        )
    }
}
