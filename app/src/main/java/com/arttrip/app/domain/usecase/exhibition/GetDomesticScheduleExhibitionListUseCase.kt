package com.arttrip.app.domain.usecase.exhibition

import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.HomeRepository
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
