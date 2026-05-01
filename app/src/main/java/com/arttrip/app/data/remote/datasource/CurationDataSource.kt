package com.arttrip.app.data.remote.datasource

import com.arttrip.app.data.remote.api.CurationApi
import javax.inject.Inject

class CurationDataSource
    @Inject
    constructor(
        private val api: CurationApi,
    ) {
        suspend fun getForeignCurations(country: String) = api.getCurations(domestic = false, country = country)

        suspend fun getDomesticCurations() = api.getCurations(domestic = true, country = null)

        suspend fun getCurationExhibits(
            curationId: Long,
            cursor: Int?,
            size: Int,
        ) = api.getCurationExhibits(curationId = curationId, cursor = cursor, size = size)
    }
