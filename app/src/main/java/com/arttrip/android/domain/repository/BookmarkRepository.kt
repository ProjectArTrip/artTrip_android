package com.arttrip.android.domain.repository

import com.arttrip.android.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun addBookmark(exhibitId: Int): Flow<ApiResult<Unit>>

    fun removeBookmark(exhibitId: Int): Flow<ApiResult<Unit>>
}
