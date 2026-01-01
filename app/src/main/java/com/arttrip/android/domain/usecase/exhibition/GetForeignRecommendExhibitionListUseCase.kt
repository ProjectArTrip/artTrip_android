package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForeignRecommendExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(country: ForeignCountry): Flow<ApiResult<List<ExhibitionModel>>> =
            homeRepository.getForeignRecommendExhibitList(country = country, width = 360, height = 480, format = "png")
    }
