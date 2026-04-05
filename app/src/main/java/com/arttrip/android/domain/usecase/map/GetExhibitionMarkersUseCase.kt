package com.arttrip.android.domain.usecase.map

import com.arttrip.android.domain.model.map.ExhibitionMarker
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExhibitionMarkersUseCase
    @Inject
    constructor(
        private val mapRepository: MapRepository,
    ) {
        operator fun invoke(etag: String): Flow<ApiResult<List<ExhibitionMarker>>> = mapRepository.getExhibitMarkers(etag = etag)
    }
