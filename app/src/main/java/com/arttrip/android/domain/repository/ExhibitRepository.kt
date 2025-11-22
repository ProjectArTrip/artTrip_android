package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.exhibit.ExhibitInfoModel
import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface ExhibitRepository {
    fun getExhibitInfo(id: Int): Flow<ApiResult<ExhibitInfoModel>>
}
