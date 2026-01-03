package com.arttrip.android.core.util.bookmark

import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.usecase.bookmark.AddBookmarkUseCase
import com.arttrip.android.domain.usecase.bookmark.RemoveBookmarkUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ExhibitionBookmarkSyncerImpl
    @Inject
    constructor(
        private val addBookmarkUseCase: AddBookmarkUseCase,
        private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    ) : ExhibitionBookmarkSyncer {
        override suspend fun sync(
            exhibitId: Int,
            target: Boolean,
        ): Boolean {
            val result =
                if (target) {
                    addBookmarkUseCase(exhibitId).first { it !is ApiResult.Loading }
                } else {
                    removeBookmarkUseCase(exhibitId).first { it !is ApiResult.Loading }
                }

            return when (result) {
                is ApiResult.Success<*> -> true
                is ApiResult.Error -> false
                is ApiResult.Loading -> false
            }
        }
    }
