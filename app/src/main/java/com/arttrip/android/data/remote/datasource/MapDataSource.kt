package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.MapApi
import javax.inject.Inject

class MapDataSource
    @Inject
    constructor(
        private val api: MapApi,
    ) {
        suspend fun getExhibitMarkers(etag: String) = api.getExhibitMarkers(etag = etag)

        suspend fun getClusterExhibits(
            ids: List<Int>,
            cursor: Int? = null,
            size: Int = 20,
        ) = api.getClusterExhibits(ids = ids, cursor = cursor, size = size)
    }
