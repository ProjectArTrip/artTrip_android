package com.arttrip.android.domain.repository

import androidx.paging.PagingData
import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.model.exhibition.ExhibitionDetail
import com.arttrip.android.domain.model.exhibition.RecentExhibition
import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface ExhibitRepository {
    fun getExhibitDetail(
        exhibitId: Int,
        imageQueryParams: ImageQueryParams,
    ): Flow<ApiResult<ExhibitionDetail>>

    fun getUserRecentExhibits(imageQueryParams: ImageQueryParams): Flow<ApiResult<List<RecentExhibition>>>
    fun getExhibits(
        query: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        isDomestic: Boolean? = null,
        country: String? = null,
        region: String? = null,
        genres: List<String>? = null,
        styles: List<String>? = null,
        sortType: SortType? = null,
        pageSize: Int = 20,
        initialLoadSize: Int = 20,
    ): Flow<PagingData<Exhibition>>
}
