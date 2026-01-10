package com.arttrip.android.presentation.home.sub.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SearchRoute(
    innerPadding: PaddingValues,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    SearchScreen(
        innerPadding = innerPadding,
    )
}
