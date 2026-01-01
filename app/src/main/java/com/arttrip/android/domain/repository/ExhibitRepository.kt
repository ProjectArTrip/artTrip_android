package com.arttrip.android.domain.repository

import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.domain.model.exhibition.ExhibitionDetailModel
import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface ExhibitRepository {
    fun getExhibitDetail(
        exhibitId: Int,
        imageQueryParams: ImageQueryParams,
    ): Flow<ApiResult<ExhibitionDetailModel>>
}
