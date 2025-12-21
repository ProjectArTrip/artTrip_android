package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import com.arttrip.android.presentation.home.DomesticRegion
import com.arttrip.android.presentation.home.ExhibitGenre
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDomesticGenreExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(
            region: DomesticRegion,
            genre: ExhibitGenre,
        ): Flow<ApiResult<List<ExhibitionModel>>> = homeRepository.getDomesticGenreExhibitList(region = region, genre = genre)
    }
