package com.arttrip.android.domain.usecase.map

import androidx.paging.PagingData
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClusterExhibitsUseCase
    @Inject
    constructor(
        private val mapRepository: MapRepository,
    ) {
        operator fun invoke(ids: List<Int>): Flow<PagingData<Exhibition>> =
            mapRepository.getClusterExhibits(ids)
    }
