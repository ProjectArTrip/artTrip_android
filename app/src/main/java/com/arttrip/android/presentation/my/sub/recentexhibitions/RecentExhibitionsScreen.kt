package com.arttrip.android.presentation.my.sub.recentexhibitions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.presentation.my.sub.recentexhibitions.contract.RecentExhibitionsIntent
import com.arttrip.android.presentation.my.sub.recentexhibitions.contract.RecentExhibitionsState

private val CONTENT_HORIZONTAL_PADDING = 24.dp
private val REVIEW_ITEM_GAP = 24.dp
private val BOTTOM_SCROLL_SPACER = 48.dp

@Composable
fun RecentExhibitionsScreen(
    innerPadding: PaddingValues,
    state: RecentExhibitionsState,
    onIntent: (RecentExhibitionsIntent) -> Unit,
) {
    Column(modifier = Modifier.padding(innerPadding)) {
        AppTopBar(
            title = "최근 본 전시",
            leading = {
                AppIconButton(
                    iconResId = R.drawable.ic_back_24,
                    contentDescription = "뒤로가기",
                    onIconClick = {
                        onIntent(RecentExhibitionsIntent.BackClicked)
                    },
                )
            },
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}
