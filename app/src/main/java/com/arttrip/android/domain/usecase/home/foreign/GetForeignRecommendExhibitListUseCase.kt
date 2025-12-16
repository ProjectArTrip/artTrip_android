package com.arttrip.android.domain.usecase.home.foreign

import ForeignExhibitListQueryModel
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForeignRecommendExhibitListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(query: ForeignExhibitListQueryModel): Flow<ApiResult<List<ExhibitModel>>> = homeRepository.getHomeRecommendExhibitList(query = query)
    }
