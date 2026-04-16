package com.arttrip.app.domain.usecase.exhibition

import com.arttrip.app.core.model.image.ImageQueryParams
import com.arttrip.app.domain.model.exhibition.RecentExhibition
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.ExhibitRepository
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
