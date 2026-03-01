package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDomesticPersonalizedExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(region: DomesticRegion): Flow<ApiResult<List<Exhibition>>> =
            homeRepository.getDomesticPersonalizedExhibitList(region = region)
    }
