package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.domain.model.exhibit.ExhibitDetailModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.ExhibitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExhibitionDetailUseCase
    @Inject
    constructor(
        private val exhibitRepository: ExhibitRepository,
    ) {
        operator fun invoke(exhibitId: Int): Flow<ApiResult<ExhibitDetailModel>> =
            exhibitRepository.getExhibitDetail(
                exhibitId = exhibitId,
            )
    }
