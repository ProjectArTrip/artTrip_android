package com.arttrip.app.domain.repository

import androidx.paging.PagingData
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.model.map.ExhibitionMarker
import com.arttrip.app.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    fun getExhibitMarkers(etag: String): Flow<ApiResult<List<ExhibitionMarker>>>

    fun getClusterExhibits(ids: List<Int>): Flow<PagingData<Exhibition>>
}
