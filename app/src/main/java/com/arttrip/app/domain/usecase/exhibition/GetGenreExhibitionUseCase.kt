package com.arttrip.app.domain.usecase.exhibition

import androidx.paging.PagingData
import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.exhibition.SortType
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import com.arttrip.app.domain.model.exhibition.Exhibition
import com.arttrip.app.domain.repository.ExhibitionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenreExhibitionUseCase
    @Inject
    constructor(
        private val exhibitionRepository: ExhibitionRepository,
    ) {
        operator fun invoke(
            country: ForeignCountry?,
            genre: ExhibitionGenre,
            sortType: SortType?,
            onTotalCountLoaded: (Int) -> Unit = {},
        ): Flow<PagingData<Exhibition>> =
            exhibitionRepository.getExhibits(
                isDomestic = country == null,
                country = country,
                genres = listOf(genre.label),
                sortType = sortType,
                onTotalCountLoaded = onTotalCountLoaded,
            )
    }
