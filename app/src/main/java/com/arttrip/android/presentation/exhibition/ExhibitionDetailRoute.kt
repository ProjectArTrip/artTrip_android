package com.arttrip.android.presentation.exhibition

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailEffect
import com.arttrip.android.presentation.exhibition.contract.ExhibitionDetailIntent
import com.arttrip.android.presentation.reviewwrite.model.ReviewWritePrefill

@Composable
fun ExhibitionDetailRoute(
    innerPadding: PaddingValues,
    exhibitId: Int,
    onBack: () -> Unit,
    onNavigateReviewWrite: (prefill: ReviewWritePrefill) -> Unit,
    reviewWriteSuccessTick: Int,
    viewModel: ExhibitionDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val density = LocalDensity.current

        val widthDp = maxWidth

        val widthPx =
            remember(widthDp) {
                with(density) { widthDp.roundToPx() }
            }
        val heightPx = remember(density) { with(density) { 280.dp.roundToPx() } }
        LaunchedEffect(exhibitId, widthPx, heightPx) {
            viewModel.onIntent(
                ExhibitionDetailIntent.Initialize(
                    exhibitId = exhibitId,
                    imageQueryParams =
                        ImageQueryParams(
                            widthPx = widthPx,
                            heightPx = heightPx,
                        ),
                ),
            )
        }

        LaunchedEffect(Unit) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    ExhibitionDetailEffect.NavigateBack -> onBack()
                    is ExhibitionDetailEffect.NavigateToWriteReview -> {
                        onNavigateReviewWrite(
                            ReviewWritePrefill(
                                effect.exhibitId,
                                effect.title,
                                effect.hallName,
                                effect.posterUrl,
                            ),
                        )
                    }
                    is ExhibitionDetailEffect.ShowError -> {
                        // error 처리
                    }
                }
            }
        }

        val reviewsFlow = remember(exhibitId) { viewModel.reviewsFlow(exhibitId) }

        ExhibitionDetailScreen(
            innerPadding = innerPadding,
            state = state,
            onIntent = viewModel::onIntent,
            reviewsFlow = reviewsFlow,
        )
    }
}
