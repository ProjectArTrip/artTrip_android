package com.arttrip.android.domain.usecase.home.foreign

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountryListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(): Flow<ApiResult<List<String>>> = homeRepository.getCountryList()
    }
