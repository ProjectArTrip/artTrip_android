package com.arttrip.android.domain.usecase.home.foreign

import ForeignExhibitListQueryModel
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import com.arttrip.android.presentation.home.ForeignCountry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForeignRecommendExhibitListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(country: ForeignCountry): Flow<ApiResult<List<ExhibitModel>>> = homeRepository.getForeignRecommendExhibitList(country = country)
    }
