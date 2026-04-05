package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.map.ExhibitionMarker
import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    fun getExhibitMarkers(etag: String): Flow<ApiResult<List<ExhibitionMarker>>>
}
