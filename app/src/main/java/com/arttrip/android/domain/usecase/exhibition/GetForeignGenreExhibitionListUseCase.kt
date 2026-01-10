package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForeignGenreExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(
            country: ForeignCountry,
            genre: ExhibitionGenre,
        ): Flow<ApiResult<List<Exhibition>>> =
            homeRepository.getForeignGenreExhibitList(country = country, genre = genre, width = 200, height = 200, format = "png")
    }
