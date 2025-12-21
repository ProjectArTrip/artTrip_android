package com.arttrip.android.domain.usecase.home.foreign

import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import com.arttrip.android.presentation.home.ForeignCountry
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetForeignScheduledExhibitListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(
            country: ForeignCountry,
            date: LocalDate,
        ): Flow<ApiResult<List<ExhibitModel>>> = homeRepository.getForeignScheduleExhibitList(country = country, date = date)
    }
