package com.arttrip.android.domain.usecase.bookmark

import androidx.paging.PagingData
import com.arttrip.android.domain.model.favorite.BookmarkSortType
import com.arttrip.android.domain.model.favorite.Favorite
import com.arttrip.android.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarksUseCase
    @Inject
    constructor(
        private val bookmarkRepository: BookmarkRepository,
    ) {
        operator fun invoke(
            sortType: BookmarkSortType,
            regions: List<String>?,
            countries: List<String>?,
        ): Flow<PagingData<Favorite>> =
            bookmarkRepository.getBookmarks(
                sortType = sortType,
                regions = regions,
                countries = countries,
            )
    }
