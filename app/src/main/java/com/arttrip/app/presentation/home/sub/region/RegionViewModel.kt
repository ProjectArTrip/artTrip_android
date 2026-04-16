package com.arttrip.app.presentation.home.sub.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.usecase.exhibition.GetRegionExhibitionUseCase
import com.arttrip.app.presentation.home.sub.region.contract.RegionEffect
import com.arttrip.app.presentation.home.sub.region.contract.RegionIntent
import com.arttrip.app.presentation.home.sub.region.contract.RegionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionViewModel
    @Inject
    constructor(
        private val getRegionExhibitionUseCase: GetRegionExhibitionUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(RegionState())
        val state: StateFlow<RegionState> = _state

        private val _effect = MutableSharedFlow<RegionEffect>()
        val effect: SharedFlow<RegionEffect> = _effect

        private val regionTrigger = MutableSharedFlow<DomesticRegion>(replay = 1)

        @OptIn(ExperimentalCoroutinesApi::class)
        val exhibitions: kotlinx.coroutines.flow.Flow<PagingData<Exhibition>> =
            regionTrigger
                .flatMapLatest { region ->
                    getRegionExhibitionUseCase(region)
                }.cachedIn(viewModelScope)

        private var isInitialized = false

        fun onIntent(intent: RegionIntent) {
            when (intent) {
                is RegionIntent.ScreenEntered -> {
                    _state.update { it.copy(selectedRegion = intent.region) }
                    if (!isInitialized) {
                        isInitialized = true
                        emitTrigger(intent.region)
                    }
                }
                RegionIntent.BackIconClicked -> {
                    viewModelScope.launch {
                        _effect.emit(RegionEffect.NavigateBack)
                    }
                }
                RegionIntent.DropdownClicked -> {
                    _state.update { it.copy(isDropdownExpanded = !it.isDropdownExpanded) }
                }
                RegionIntent.DropdownDismissed -> {
                    _state.update { it.copy(isDropdownExpanded = false) }
                }
                is RegionIntent.RegionSelected -> {
                    _state.update {
                        it.copy(
                            selectedRegion = intent.region,
                            isDropdownExpanded = false,
                        )
                    }
                    emitTrigger(intent.region)
                }
                is RegionIntent.ExhibitionClicked -> {
                    viewModelScope.launch {
                        _effect.emit(RegionEffect.NavigateToDetail(intent.id))
                    }
                }
                is RegionIntent.LikeClicked -> {}
            }
        }

        private fun emitTrigger(region: DomesticRegion) {
            viewModelScope.launch {
                regionTrigger.emit(region)
            }
        }
    }
