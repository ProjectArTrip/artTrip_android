package com.arttrip.app.domain.usecase.exhibition

import androidx.paging.PagingData
import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.repository.ExhibitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleExhibitionUseCase
    @Inject
    constructor(
        private val exhibitRepository: ExhibitRepository,
    ) {
        operator fun invoke(
            country: ForeignCountry?,
            startDate: String,
            endDate: String,
        ): Flow<PagingData<Exhibition>> =
            exhibitRepository.getExhibits(
                startDate = startDate,
                endDate = endDate,
                isDomestic = country == null,
                country = country,
                region = if (country == null) DomesticRegion.Entire else null,
            )
    }
