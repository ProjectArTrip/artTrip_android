package com.arttrip.android.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    innerPadding: PaddingValues,
    onNavigate: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeState by viewModel.uiState.collectAsStateWithLifecycle()

    // 이동 예시
//    LaunchedEffect(Unit) {
//        viewModel.effect.collect { effect ->
//            when (effect) {
//                HomeEffect.NavigateToDateFilter -> {
//                    onNavigate(AppRoute.HOME_DATE_FILTER)
//                }
//                is HomeEffect.ShowToast -> {
//                    // 토스트, 스낵바 같은 UI 사이드 이펙트 처리
//                }
//            }
//        }
//    }
    HomeScreen(
        innerPadding = innerPadding,
        uiState = homeState,
        onIntent = viewModel::onIntent,
    )
}
