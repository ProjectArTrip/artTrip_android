package com.arttrip.android.domain.usecase.home.domestic

import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDomesticScheduledExhibitListUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {
    operator fun invoke(date: String): Flow<ApiResult<List<ExhibitModel>>> = homeRepository.getHomeScheduleExhibitList(isDomestic = true, date = date)
}