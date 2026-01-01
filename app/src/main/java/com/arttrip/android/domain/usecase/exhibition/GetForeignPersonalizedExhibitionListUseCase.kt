package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForeignPersonalizedExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(country: ForeignCountry): Flow<ApiResult<List<ExhibitionModel>>> =
            homeRepository.getForeignPersonalizedExhibitList(country = country, width = 240, height = 300, format = "png")
    }
