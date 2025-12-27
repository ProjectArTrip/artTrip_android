package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDomesticGenreExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(
            region: DomesticRegion,
            genre: ExhibitionGenre,
        ): Flow<ApiResult<List<ExhibitionModel>>> = homeRepository.getDomesticGenreExhibitList(region = region, genre = genre, width = 200, height = 200, format = "png")
    }
