package com.arttrip.android.presentation.my

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.my.contract.MyPageEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyPageRoute(
    innerPadding: PaddingValues,
    onNavigateEditProfile: () -> Unit,
    onNavigateRecentExhibitions: () -> Unit,
    onNavigateMyReviews: () -> Unit,
    onNavigateTasteAnalysis: () -> Unit,
    onNavigateSettings: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                MyPageEffect.NavigateToEditProfile -> onNavigateEditProfile()
                MyPageEffect.NavigateToRecentExhibitions -> onNavigateRecentExhibitions()
                MyPageEffect.NavigateToMyReviews -> onNavigateMyReviews()
                MyPageEffect.NavigateToTasteAnalysis -> onNavigateTasteAnalysis()
                MyPageEffect.NavigateToSettings -> onNavigateSettings()
            }
        }
    }
    MyPageScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
