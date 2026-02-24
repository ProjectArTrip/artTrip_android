package com.arttrip.android.domain.usecase.bookmark

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddBookmarkUseCase
    @Inject
    constructor(
        private val bookmarkRepository: BookmarkRepository,
    ) {
        operator fun invoke(exhibitId: Int): Flow<ApiResult<Unit>> =
            bookmarkRepository.addBookmark(
                exhibitId = exhibitId,
            )
    }
