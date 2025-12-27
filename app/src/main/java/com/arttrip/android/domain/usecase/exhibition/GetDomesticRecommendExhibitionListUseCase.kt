package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDomesticRecommendExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(region: DomesticRegion): Flow<ApiResult<List<ExhibitionModel>>> =
            homeRepository.getDomesticRecommendExhibitList(region = region, width = 360, height = 480, format = "png")
    }
