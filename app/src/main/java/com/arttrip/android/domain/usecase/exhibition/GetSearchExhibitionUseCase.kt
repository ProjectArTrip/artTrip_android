package com.arttrip.android.domain.usecase.exhibition

import androidx.paging.PagingData
import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.repository.ExhibitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchExhibitionUseCase
    @Inject
    constructor(
        private val exhibitRepository: ExhibitRepository,
    ) {
        operator fun invoke(
            query: String
        ): Flow<PagingData<Exhibition>> =
            exhibitRepository.getExhibits(
                query = query
            )
    }
