package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetDomesticScheduleExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(
            region: DomesticRegion,
            date: LocalDate,
        ): Flow<ApiResult<List<Exhibition>>> = homeRepository.getDomesticScheduleExhibitList(region = region, date = date)
    }
