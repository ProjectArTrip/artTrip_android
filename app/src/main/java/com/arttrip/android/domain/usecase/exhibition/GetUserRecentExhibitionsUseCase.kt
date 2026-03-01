package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.domain.model.exhibition.RecentExhibition
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.ExhibitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRecentExhibitionsUseCase
    @Inject
    constructor(
        private val exhibitRepository: ExhibitRepository,
    ) {
        operator fun invoke(imageQueryParams: ImageQueryParams): Flow<ApiResult<List<RecentExhibition>>> =
            exhibitRepository.getUserRecentExhibits(
                imageQueryParams = imageQueryParams,
            )
    }
