package com.arttrip.app.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.usecase.map.GetClusterExhibitsUseCase
import com.arttrip.app.domain.usecase.map.GetExhibitionMarkersUseCase
import com.arttrip.app.presentation.map.contract.MapEffect
import com.arttrip.app.presentation.map.contract.MapIntent
import com.arttrip.app.presentation.map.contract.MapState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel
    @Inject
    constructor(
        private val getExhibitionMarkersUseCase: GetExhibitionMarkersUseCase,
        private val getClusterExhibitsUseCase: GetClusterExhibitsUseCase,
        private val fusedLocationClient: FusedLocationProviderClient,
    ) : ViewModel() {
        private val _state = MutableStateFlow(MapState())
        val state: StateFlow<MapState> = _state

        private val _effect = MutableSharedFlow<MapEffect>()
        val effect: SharedFlow<MapEffect> = _effect

        private var skipNextCameraIdle = false

        @OptIn(ExperimentalCoroutinesApi::class)
        val clusterExhibits: Flow<PagingData<Exhibition>> =
            _state
                .map { it.selectedIds }
                .distinctUntilChanged()
                .flatMapLatest { ids ->
                    if (ids.isEmpty()) {
                        flowOf(PagingData.empty())
                    } else {
                        getClusterExhibitsUseCase(ids)
                    }
                }.cachedIn(viewModelScope)

        init {
            onIntent(MapIntent.LoadMarkers(etag = ""))
        }

        fun onIntent(intent: MapIntent) {
            when (intent) {
                is MapIntent.LoadMarkers -> loadMarkers(etag = intent.etag)
                is MapIntent.OnClusterClicked -> {
                    skipNextCameraIdle = true
                    _state.update { it.copy(selectedClusterCount = intent.count, selectedIds = intent.ids) }
                }
                is MapIntent.OnCameraIdle -> {
                    if (skipNextCameraIdle) {
                        skipNextCameraIdle = false
                        return
                    }
                    _state.update { it.copy(selectedClusterCount = intent.visibleCount, selectedIds = intent.ids) }
                }
                is MapIntent.OnLocationPermissionGranted -> fetchCurrentLocation()
                is MapIntent.OnLocationPermissionDenied -> Unit
                is MapIntent.ExhibitionClicked -> {
                    viewModelScope.launch {
                        _effect.emit(MapEffect.NavigateToExhibitionDetail(intent.id))
                    }
                }
                is MapIntent.OnCameraMoved -> {
                    _state.update { it.copy(cameraLatLng = intent.latLng, cameraZoom = intent.zoom) }
                }
                is MapIntent.OnCountrySelected -> {
                    _state.update { it.copy(selectedCountry = intent.country) }
                }
                is MapIntent.OnLocationCentered -> {
                    _state.update { it.copy(hasCenteredOnLocation = true) }
                }
            }
        }

        private fun fetchCurrentLocation() {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        _state.update { state -> state.copy(currentLocation = LatLng(it.latitude, it.longitude)) }
                    }
                }
            } catch (e: SecurityException) {
                // 권한이 없는 경우 무시 (서울 기본값 유지)
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
