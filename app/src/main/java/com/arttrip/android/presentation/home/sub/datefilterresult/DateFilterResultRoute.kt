package com.arttrip.android.presentation.home.sub.datefilterresult

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.android.core.util.LocalToastController
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultEffect
import com.arttrip.android.presentation.home.sub.datefilterresult.contract.DateFilterResultIntent
import java.time.LocalDate

@Composable
fun DateFilterResultRoute(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    isDomestic: Boolean,
    location: String,
    startDate: LocalDate,
    endDate: LocalDate,
    viewModel: DateFilterResultViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val exhibitionItems = viewModel.exhibitionsFlow.collectAsLazyPagingItems()
    val toast = LocalToastController.current

    LaunchedEffect(exhibitionItems.loadState.refresh) {
        if (exhibitionItems.loadState.refresh is LoadState.Error) {
            viewModel.onIntent(DateFilterResultIntent.PagingRefreshError)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(
            DateFilterResultIntent.Initialize(
                isDomestic = isDomestic,
                location = location,
                startDate = startDate,
                endDate = endDate,
            ),
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                DateFilterResultEffect.NavigateBack -> onBack()
                is DateFilterResultEffect.ShowToast -> toast.show(effect.message)
            }
        }
    }

    DateFilterResultScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
        exhibitionItems = exhibitionItems,
    )
}
