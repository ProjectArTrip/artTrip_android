package com.arttrip.app.domain.usecase.exhibition

import androidx.paging.PagingData
import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.repository.ExhibitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRegionExhibitionUseCase
    @Inject
    constructor(
        private val exhibitRepository: ExhibitRepository,
    ) {
        operator fun invoke(region: DomesticRegion): Flow<PagingData<Exhibition>> =
            exhibitRepository.getExhibits(
                isDomestic = true,
                region = region,
            )
    }
