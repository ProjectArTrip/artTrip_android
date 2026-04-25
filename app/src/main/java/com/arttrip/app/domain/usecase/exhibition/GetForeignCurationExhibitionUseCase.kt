package com.arttrip.app.domain.usecase.exhibition

import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.domain.model.curation.Curation
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.CurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForeignCurationExhibitionUseCase
    @Inject
    constructor(
        private val curationRepository: CurationRepository,
    ) {
        operator fun invoke(country: ForeignCountry): Flow<ApiResult<Curation>> =
            curationRepository.getForeignCurations(country = country)
    }
