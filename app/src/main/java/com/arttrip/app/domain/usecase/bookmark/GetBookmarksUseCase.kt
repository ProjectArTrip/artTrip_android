package com.arttrip.app.domain.usecase.bookmark

import androidx.paging.PagingData
import com.arttrip.app.domain.model.favorite.Bookmark
import com.arttrip.app.domain.model.favorite.BookmarkSortType
import com.arttrip.app.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarksUseCase
    @Inject
    constructor(
        private val bookmarkRepository: BookmarkRepository,
    ) {
        operator fun invoke(
            sortType: BookmarkSortType,
            region: String?,
            country: String?,
        ): Flow<PagingData<Bookmark>> =
            bookmarkRepository.getBookmarks(
                sortType = sortType,
                region = region,
                country = country,
            )
    }
