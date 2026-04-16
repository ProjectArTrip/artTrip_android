package com.arttrip.app.domain.usecase.exhibition

import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.HomeRepository
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
        ): Flow<ApiResult<List<Exhibition>>> = homeRepository.getDomesticGenreExhibitList(region = region, genre = genre)
    }
