package com.arttrip.app.presentation.mypage.sub.settings.sub.notice

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.app.presentation.mypage.sub.settings.sub.notice.contract.NoticeEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NoticeRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    viewModel: NoticeViewModel = hiltViewModel(),
) {
    val reviewsFlow = viewModel.noticesFlow
    val noticeItems = reviewsFlow.collectAsLazyPagingItems()

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                NoticeEffect.NavigateBack -> onBack()
            }
        }
    }
    NoticeScreen(
        innerPadding = innerPadding,
        onIntent = viewModel::onIntent,
        noticeItems = noticeItems,
    )
}
