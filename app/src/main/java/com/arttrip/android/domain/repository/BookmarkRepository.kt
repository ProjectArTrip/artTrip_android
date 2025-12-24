package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.bookmark.BookmarkResultModel
import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun addBookmark(exhibitId: Int): Flow<ApiResult<BookmarkResultModel>>

    fun removeBookmark(exhibitId: Int): Flow<ApiResult<Unit>>
}
