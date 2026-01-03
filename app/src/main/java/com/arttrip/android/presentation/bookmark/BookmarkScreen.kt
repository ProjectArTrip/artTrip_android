package com.arttrip.android.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
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
        AppTopBar(
            title = "즐겨찾기",
            actions = {
                AppIconButton(
                    iconResId = R.drawable.ic_alert_24,
                    onIconClick = {},
                )
            },
        )
    }
}
