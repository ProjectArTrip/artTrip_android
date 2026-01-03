package com.arttrip.android.data.remote.datasource

import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.data.remote.api.ExhibitApi
import javax.inject.Inject

class ExhibitDataSource
    @Inject
    constructor(
        private val api: ExhibitApi,
    ) {
        suspend fun getExhibitDetail(
            id: Int,
            image: ImageQueryParams,
        ) = api.getExhibitDetail(
            id = id,
            w = image.widthPx,
            h = image.heightPx,
            f = image.format.value,
        )
    }
