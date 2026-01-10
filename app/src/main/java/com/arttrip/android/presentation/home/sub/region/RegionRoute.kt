package com.arttrip.android.presentation.home.sub.region

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.presentation.home.sub.region.contract.RegionEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegionRoute(
    innerPadding: PaddingValues,
    viewModel: RegionViewModel = hiltViewModel(),
    region: DomesticRegion,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                RegionEffect.NavigateBack -> onBack()
            }
        }
    }

    RegionScreen(
        innerPadding = innerPadding,
        state = state,
        onIntent = viewModel::onIntent,
        region = region
    )
}
