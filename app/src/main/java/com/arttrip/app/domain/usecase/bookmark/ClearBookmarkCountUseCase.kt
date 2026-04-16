package com.arttrip.app.domain.usecase.bookmark

import com.arttrip.app.domain.repository.BookmarkRepository
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
