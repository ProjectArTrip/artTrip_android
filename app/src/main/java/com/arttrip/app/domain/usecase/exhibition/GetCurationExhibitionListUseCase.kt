package com.arttrip.app.domain.usecase.exhibition

import androidx.paging.PagingData
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.repository.CurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurationExhibitionListUseCase
    @Inject
    constructor(
        private val curationRepository: CurationRepository,
    ) {
        operator fun invoke(
            curationId: Long,
            onTitleLoaded: (String) -> Unit = {},
        ): Flow<PagingData<Exhibition>> =
            curationRepository.getCurationExhibits(
                curationId = curationId,
                onTitleLoaded = onTitleLoaded,
            )
    }
