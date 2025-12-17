package com.arttrip.android.domain.usecase.home.foreign

import ForeignExhibitListQueryModel
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import com.arttrip.android.presentation.home.ExhibitGenre
import com.arttrip.android.presentation.home.ForeignCountry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForeignGenreExhibitListUseCase
@Inject
constructor(
    private val homeRepository: HomeRepository,
) {
    operator fun invoke(country: ForeignCountry, genre: ExhibitGenre): Flow<ApiResult<List<ExhibitModel>>> =
        homeRepository.getHomeGenreExhibitList(place = country, genre = genre)
}
