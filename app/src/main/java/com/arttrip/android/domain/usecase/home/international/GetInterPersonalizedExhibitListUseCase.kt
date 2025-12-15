package com.arttrip.android.domain.usecase.home.international

import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInterPersonalizedExhibitListUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {
    operator fun invoke(): Flow<ApiResult<List<ExhibitModel>>> = homeRepository.getHomePersonalizedExhibitList(isDomestic = false)
}