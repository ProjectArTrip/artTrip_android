package com.arttrip.android.presentation.my

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MyPageRoute(
    innerPadding: PaddingValues,
    onNavigateExhibitionDetail: (Int) -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MyPageScreen(
        innerPadding = innerPadding,
        state = state,
        onNavigateExhibitionDetail = onNavigateExhibitionDetail,
    )
}
