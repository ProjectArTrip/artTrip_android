package com.arttrip.app.presentation.home.sub.schedule

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.presentation.home.sub.schedule.contract.ScheduleEffect
import com.arttrip.app.presentation.home.sub.schedule.contract.ScheduleIntent
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@Composable
fun ScheduleRoute(
    innerPadding: PaddingValues,
    hasUnread: Boolean = false,
    viewModel: ScheduleViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onNavigateNotification: () -> Unit,
    onNavigateToExhibitionDetail: (Int) -> Unit,
    country: ForeignCountry?,
    date: LocalDate,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val bookmarked by viewModel.bookmarked.collectAsStateWithLifecycle()
    val exhibitionList = viewModel.exhibitions.collectAsLazyPagingItems()

    LaunchedEffect(date) {
        viewModel.onIntent(ScheduleIntent.Initialize(date = date, country = country))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                ScheduleEffect.NavigateBack -> onBack()
                ScheduleEffect.NavigateToNotification -> onNavigateNotification()
                is ScheduleEffect.NavigateToExhibitionDetail -> {
                    onNavigateToExhibitionDetail(effect.exhibitionId)
                }
            }
        }
    }

    ScheduleScreen(
        innerPadding = innerPadding,
        state = state,
        hasUnread = hasUnread,
        onIntent = viewModel::onIntent,
        date = date,
        exhibitionList = exhibitionList,
        bookmarked = bookmarked,
    )
}
