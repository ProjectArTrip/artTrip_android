package com.arttrip.app.domain.usecase.exhibition

import androidx.paging.PagingData
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.repository.ExhibitionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchExhibitionUseCase
    @Inject
    constructor(
        private val exhibitionRepository: ExhibitionRepository,
    ) {
        operator fun invoke(query: String): Flow<PagingData<Exhibition>> =
            exhibitionRepository.getExhibits(
                query = query,
            )
    }
