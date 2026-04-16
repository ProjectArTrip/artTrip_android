package com.arttrip.app.domain.usecase.map

import com.arttrip.app.domain.model.map.ExhibitionMarker
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExhibitionMarkersUseCase
    @Inject
    constructor(
        private val mapRepository: MapRepository,
    ) {
        operator fun invoke(etag: String): Flow<ApiResult<List<ExhibitionMarker>>> = mapRepository.getExhibitMarkers(etag = etag)
    }
