package com.arttrip.android.data.remote.datasource

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
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
            country = country?.takeIf { it != ForeignCountry.Entire }?.label,
            region = region?.takeIf { it != DomesticRegion.Entire }?.label,
            genres = genres,
            styles = styles,
            sortType = sortType,
        )
    }
