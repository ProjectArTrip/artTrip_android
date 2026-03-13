package com.arttrip.android.data.remote.datasource

import com.arttrip.android.core.model.enums.exhibition.SortType
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

        suspend fun getExhibits(
            cursor: Int?,
            size: Int,
            query: String?,
            startDate: String?,
            endDate: String?,
            isDomestic: Boolean?,
            country: String?,
            region: String?,
            genres: List<String>?,
            styles: List<String>?,
            sortType: SortType?,
        ) = api.getExhibits(
            cursor = cursor,
            size = size,
            query = query,
            startDate = startDate,
            endDate = endDate,
            isDomestic = isDomestic,
            country = country,
            region = region,
            genres = genres,
            styles = styles,
            sortType = sortType,
        )
    }
