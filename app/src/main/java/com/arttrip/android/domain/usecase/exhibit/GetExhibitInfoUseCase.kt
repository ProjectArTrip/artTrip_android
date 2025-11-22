package com.arttrip.android.domain.usecase.exhibit

import com.arttrip.android.domain.model.exhibit.ExhibitInfoModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.ExhibitRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetExhibitInfoUseCase
    @Inject
    constructor(
        private val exhibitRepository: ExhibitRepository,
    ) {
        operator fun invoke(id: Int): Flow<ApiResult<ExhibitInfoModel>> = exhibitRepository.getExhibitInfo(id)
    }
