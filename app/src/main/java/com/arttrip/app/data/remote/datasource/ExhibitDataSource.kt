package com.arttrip.app.data.remote.datasource

import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.exhibition.SortType
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.core.model.image.ImageQueryParams
import com.arttrip.app.data.remote.api.ExhibitApi
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
            country: ForeignCountry?,
            region: DomesticRegion?,
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
            country = country?.label,
            region = region?.label,
            genres = genres,
            styles = styles,
            sortType = sortType,
        )
    }
