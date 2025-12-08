package com.arttrip.android.domain.usecase.home

import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeRecommendExhibitListUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {
    operator fun invoke(isDomestic: Boolean): Flow<ApiResult<List<ExhibitModel>>> = homeRepository.getHomeRecommendExhibitList(isDomestic = isDomestic)
}