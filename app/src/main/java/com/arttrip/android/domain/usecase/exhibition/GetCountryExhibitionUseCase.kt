package com.arttrip.android.domain.usecase.exhibition

import androidx.paging.PagingData
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.repository.ExhibitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountryExhibitionUseCase
    @Inject
    constructor(
        private val exhibitRepository: ExhibitRepository,
    ) {
        operator fun invoke(country: ForeignCountry): Flow<PagingData<Exhibition>> =
            exhibitRepository.getExhibits(
                isDomestic = false,
                country = country,
            )
    }
