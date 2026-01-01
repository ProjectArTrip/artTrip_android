package com.arttrip.android.presentation.exhibition.sub.reviewwrite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arttrip.android.R
import com.arttrip.android.core.ui.component.appbar.AppTopBar
import com.arttrip.android.core.ui.component.button.AppIconButton
import com.arttrip.android.core.ui.theme.AppColor
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract.ReviewWriteIntent
import com.arttrip.android.presentation.exhibition.sub.reviewwrite.contract.ReviewWriteState

@Composable
fun ReviewWriteScreen(
    innerPadding: PaddingValues,
    state: ReviewWriteState,
    onIntent: (ReviewWriteIntent) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(AppColor.Gray0)
                .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppTopBar(
            leading = {
                AppIconButton(
                    iconResId = R.drawable.ic_close_24,
                    contentDescription = "뒤로가기",
                ) {
                    onIntent(ReviewWriteIntent.BackClicked)
                }
            },
            title = "리뷰 작성",
        )
    }
}
