package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.MapApi
import javax.inject.Inject

class MapDataSource
    @Inject
    constructor(
        private val api: MapApi,
    ) {
        suspend fun getExhibitMarkers(etag: String) = api.getExhibitMarkers(etag = etag)
    }
