package com.arttrip.android.presentation.home.sub.region

import androidx.lifecycle.ViewModel
import com.arttrip.android.presentation.home.sub.region.contract.RegionEffect
import com.arttrip.android.presentation.home.sub.region.contract.RegionIntent
import com.arttrip.android.presentation.home.sub.region.contract.RegionState
import com.arttrip.android.presentation.home.sub.search.contract.SearchEffect
import com.arttrip.android.presentation.home.sub.search.contract.SearchIntent
import com.arttrip.android.presentation.home.sub.search.contract.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class
RegionViewModel@Inject
    constructor() : ViewModel() {
    private val _state = MutableStateFlow(RegionState())
    val state: StateFlow<RegionState> = _state

    private val _effect = MutableSharedFlow<RegionEffect>()
    val effect: SharedFlow<RegionEffect> = _effect

    fun onIntent(intent: RegionIntent) {
        when (intent) {
            RegionIntent.BackIconClicked -> {}
            RegionIntent.DownIconClicked -> {}
            is RegionIntent.ExhibitionClicked -> {}
            is RegionIntent.LikeClicked -> {}
        }
    }
    }
