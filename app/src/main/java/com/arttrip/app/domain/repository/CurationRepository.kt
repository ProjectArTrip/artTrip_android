package com.arttrip.app.domain.repository

import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.domain.model.curation.Curation
import com.arttrip.app.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface CurationRepository {
    fun getForeignCurations(country: ForeignCountry): Flow<ApiResult<Curation>>

    fun getDomesticCurations(): Flow<ApiResult<Curation>>
}