package com.arttrip.android.domain.usecase.home.domestic

import DomesticExhibitListQueryModel
import com.arttrip.android.domain.model.home.ExhibitModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDomesticGenreExhibitListUseCase
@Inject
constructor(
    private val homeRepository: HomeRepository,
) {
    operator fun invoke(query: DomesticExhibitListQueryModel): Flow<ApiResult<List<ExhibitModel>>> = homeRepository.getHomeGenreExhibitList(query = query)
}
