package com.arttrip.android.presentation.mypage.sub.myreviews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.android.presentation.mypage.sub.myreviews.contract.MyReviewsEffect
import com.arttrip.android.presentation.mypage.sub.myreviews.contract.MyReviewsIntent
import com.arttrip.android.presentation.reviewwrite.model.ReviewEditPrefill
import com.arttrip.android.presentation.reviewwrite.model.ReviewWriteMode
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyReviewsRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    onNavigateReviewWrite: (mode: ReviewWriteMode) -> Unit,
    reviewWriteSuccessTick: Int,
    viewModel: MyReviewsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val reviewsFlow = viewModel.reviewsFlow
    val reviewItems = reviewsFlow.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { eff ->
            when (eff) {
                MyReviewsEffect.NavigateBack -> onBack()

                is MyReviewsEffect.NavigateToReviewEdit -> {
                    onNavigateReviewWrite(
                        ReviewWriteMode.Edit(
                            prefill =
                                ReviewEditPrefill(
                                    reviewId = eff.reviewId,
                                    title = eff.title,
                                    hallName = eff.hallName,
                                    posterUrl = eff.posterUrl,
                                ),
                        ),
                    )
                }
                MyReviewsEffect.RefreshReviews -> reviewItems.refresh()
            }
        }
    }

    LaunchedEffect(reviewWriteSuccessTick) {
        if (reviewWriteSuccessTick > 0) {
            viewModel.onIntent(MyReviewsIntent.OnReviewEditSuccess)
        }
    }
    MyReviewsScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
        reviewItems = reviewItems,
    )
}
