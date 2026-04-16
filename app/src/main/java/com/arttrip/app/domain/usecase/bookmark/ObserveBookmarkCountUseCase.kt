package com.arttrip.app.domain.usecase.bookmark

import com.arttrip.app.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveBookmarkCountUseCase
    @Inject
    constructor(
        private val repo: BookmarkRepository,
    ) {
        operator fun invoke(): StateFlow<Int?> = repo.bookmarkTotalCount
    }
