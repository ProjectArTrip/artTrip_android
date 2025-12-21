package com.arttrip.android.domain.usecase.bookmark

import com.arttrip.android.domain.model.bookmark.BookmarkCheckModel
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckBookmarkUseCase
    @Inject
    constructor(
        private val bookmarkRepository: BookmarkRepository,
    ) {
        operator fun invoke(exhibitId: Int): Flow<ApiResult<BookmarkCheckModel>> =
            bookmarkRepository.checkBookmark(
                exhibitId = exhibitId,
            )
    }
