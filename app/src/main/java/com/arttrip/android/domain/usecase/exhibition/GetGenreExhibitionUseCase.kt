package com.arttrip.android.domain.usecase.exhibition

import androidx.paging.PagingData
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.repository.ExhibitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenreExhibitionUseCase
    @Inject
    constructor(
        private val exhibitRepository: ExhibitRepository,
    ) {
        operator fun invoke(
            country: ForeignCountry?,
            genre: ExhibitionGenre,
            sortType: SortType?,
            onTotalCountLoaded: (Int) -> Unit = {},
        ): Flow<PagingData<Exhibition>> =
            exhibitRepository.getExhibits(
                isDomestic = country == null,
                country = country,
                genres = listOf(genre.label),
                sortType = sortType,
                onTotalCountLoaded = onTotalCountLoaded,
            )
    }
