package com.arttrip.android.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.map.GetExhibitionMarkersUseCase
import com.arttrip.android.presentation.map.contract.MapEffect
import com.arttrip.android.presentation.map.contract.MapIntent
import com.arttrip.android.presentation.map.contract.MapState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel
    @Inject
    constructor(
        private val getExhibitionMarkersUseCase: GetExhibitionMarkersUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(MapState())
        val state: StateFlow<MapState> = _state

        private val _effect = MutableSharedFlow<MapEffect>()
        val effect: SharedFlow<MapEffect> = _effect

        init {
            onIntent(MapIntent.LoadMarkers(etag = ""))
        }

        fun onIntent(intent: MapIntent) {
            when (intent) {
                is MapIntent.LoadMarkers -> loadMarkers(etag = intent.etag)
                is MapIntent.OnClusterClicked -> _state.update { it.copy(selectedClusterCount = intent.count) }
                is MapIntent.OnCameraIdle -> _state.update { it.copy(selectedClusterCount = intent.visibleCount) }
            }
        }

        private fun loadMarkers(etag: String) {
            viewModelScope.launch {
                getExhibitionMarkersUseCase(etag = etag).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> _state.update { it.copy(isLoading = true, error = null) }
                        is ApiResult.Success -> _state.update { it.copy(isLoading = false, markers = result.data) }
                        is ApiResult.Error -> _state.update { it.copy(isLoading = false, error = result.error) }
                    }
                }
            }
        }
    }
