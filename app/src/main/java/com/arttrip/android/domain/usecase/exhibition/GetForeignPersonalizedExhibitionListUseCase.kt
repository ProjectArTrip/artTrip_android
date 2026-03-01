package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForeignPersonalizedExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(country: ForeignCountry): Flow<ApiResult<List<Exhibition>>> =
            homeRepository.getForeignPersonalizedExhibitList(country = country)
    }
