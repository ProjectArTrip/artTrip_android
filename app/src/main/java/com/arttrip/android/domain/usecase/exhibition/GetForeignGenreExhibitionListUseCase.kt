package com.arttrip.android.domain.usecase.exhibition

import com.arttrip.android.domain.model.exhibition.ExhibitionModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.ForeignCountry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForeignGenreExhibitionListUseCase
    @Inject
    constructor(
        private val homeRepository: HomeRepository,
    ) {
        operator fun invoke(
            country: ForeignCountry,
            genre: ExhibitGenre,
        ): Flow<ApiResult<List<ExhibitionModel>>> = homeRepository.getForeignGenreExhibitList(country = country, genre = genre)
    }
