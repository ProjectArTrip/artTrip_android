package com.arttrip.app.domain.usecase.exhibition

import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForeignRecommendExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(country: ForeignCountry): Flow<ApiResult<List<Exhibition>>> =
            homeRepository.getForeignRecommendExhibitList(country = country)
    }
