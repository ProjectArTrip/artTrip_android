package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetForeignScheduledExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(
            country: ForeignCountry,
            date: LocalDate,
        ): Flow<ApiResult<List<Exhibition>>> =
            homeRepository.getForeignScheduleExhibitList(country = country, date = date, width = 200, height = 200, format = "png")
    }
