package com.arttrip.app.domain.usecase.exhibition

import androidx.paging.PagingData
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.repository.ExhibitionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountryExhibitionUseCase
    @Inject
    constructor(
        private val exhibitionRepository: ExhibitionRepository,
    ) {
        operator fun invoke(country: ForeignCountry): Flow<PagingData<Exhibition>> =
            exhibitionRepository.getExhibits(
                isDomestic = false,
                country = country,
            )
    }
