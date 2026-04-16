package com.arttrip.app.domain.repository

import androidx.paging.PagingData
import com.arttrip.app.domain.model.favorite.Bookmark
import com.arttrip.app.domain.model.favorite.BookmarkSortType
import com.arttrip.app.domain.model.network.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BookmarkRepository {
    val bookmarkTotalCount: StateFlow<Int?>

    fun addBookmark(exhibitId: Int): Flow<ApiResult<Unit>>

    fun removeBookmark(exhibitId: Int): Flow<ApiResult<Unit>>

    fun getBookmarks(
        pageSize: Int = 10,
        initialLoadSize: Int = 10,
        sortType: BookmarkSortType,
        regions: List<String>? = null,
        countries: List<String>? = null,
        cursor: Int? = null,
    ): Flow<PagingData<Bookmark>>

    fun clearBookmarkTotalCount()
}
