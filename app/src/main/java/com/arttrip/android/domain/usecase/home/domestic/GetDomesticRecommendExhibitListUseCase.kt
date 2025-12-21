package com.arttrip.android.domain.usecase.home.domestic

import DomesticExhibitListQueryModel
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import com.arttrip.android.presentation.home.DomesticRegion
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDomesticRecommendExhibitListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(region: DomesticRegion): Flow<ApiResult<List<ExhibitModel>>> = homeRepository.getDomesticRecommendExhibitList(region = region)
    }
