package com.arttrip.android.data.repository

import com.arttrip.android.data.remote.datasource.MapDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.map.toDomain
import com.arttrip.android.domain.model.map.ExhibitionMarker
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class MapRepositoryImpl
    @Inject
    constructor(
        private val dataSource: MapDataSource,
    ) : MapRepository {
        override fun getExhibitMarkers(etag: String): Flow<ApiResult<List<ExhibitionMarker>>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val result = dataSource.getExhibitMarkers(etag = etag).toDomain()
                    emit(ApiResult.Success(result))
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    emit(ApiResult.Error(e.toAppError()))
                }
            }
    }
