package com.arttrip.android.domain.usecase.home.domestic

import DomesticExhibitListQueryModel
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import com.arttrip.android.presentation.home.DomesticRegion
import com.arttrip.android.presentation.home.Place
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetDomesticScheduledExhibitListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(region: DomesticRegion, date: LocalDate): Flow<ApiResult<List<ExhibitModel>>> =
            homeRepository.getHomeScheduleExhibitList(place = region, date = date)
    }
