package com.arttrip.app.domain.usecase.exhibition

import com.arttrip.app.core.model.image.ImageQueryParams
import com.arttrip.app.domain.model.exhibition.ExhibitionDetail
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.ExhibitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExhibitionDetailUseCase
    @Inject
    constructor(
        private val exhibitRepository: ExhibitRepository,
    ) {
        operator fun invoke(
            exhibitId: Int,
            imageQueryParams: ImageQueryParams,
        ): Flow<ApiResult<ExhibitionDetail>> =
            exhibitRepository.getExhibitDetail(
                exhibitId = exhibitId,
                imageQueryParams = imageQueryParams,
            )
    }
