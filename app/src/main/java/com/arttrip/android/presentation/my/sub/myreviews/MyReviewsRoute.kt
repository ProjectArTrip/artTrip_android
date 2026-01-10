package com.arttrip.android.presentation.my.sub.myreviews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.presentation.my.sub.myreviews.contract.MyReviewsEffect
import com.arttrip.android.presentation.reviewwrite.model.ReviewWritePrefill
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyReviewsRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    onNavigateReviewWrite: (exhibitId: Int, prefill: ReviewWritePrefill) -> Unit,
    viewModel: MyReviewsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { eff ->
            when (eff) {
                MyReviewsEffect.NavigateBack -> onBack()
                is MyReviewsEffect.NavigateToReviewEdit -> {
                    onNavigateReviewWrite(
                        1,
                        ReviewWritePrefill(
                            exhibitId = eff.id,
                            title = eff.title,
                            hallName = eff.hallName,
                            posterUrl = eff.posterUrl,
                            content = eff.reviewText,
                        ),
                    )
                }
            }
        }
    }
    MyReviewsScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
    )
}
