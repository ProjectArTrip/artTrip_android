package com.arttrip.android.domain.usecase.bookmark

import com.arttrip.android.domain.repository.BookmarkRepository
import javax.inject.Inject

class ClearBookmarkCountUseCase
    @Inject
    constructor(
        private val repo: BookmarkRepository,
    ) {
        operator fun invoke() {
            repo.clearBookmarkTotalCount()
        }
    }
