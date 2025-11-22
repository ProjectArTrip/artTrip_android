package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.ExhibitApi
import javax.inject.Inject

class ExhibitDataSource
    @Inject
    constructor(
        private val api: ExhibitApi,
    ) {
        suspend fun getExhibitInfo(id: Int) = api.getExhibitInfo(id)
    }
