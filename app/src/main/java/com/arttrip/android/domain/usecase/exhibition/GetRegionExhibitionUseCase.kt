package com.arttrip.android.domain.usecase.exhibition

import androidx.paging.PagingData
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.repository.ExhibitRepository
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
