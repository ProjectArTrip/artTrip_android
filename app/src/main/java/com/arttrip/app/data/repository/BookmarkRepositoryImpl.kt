package com.arttrip.app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arttrip.app.data.remote.datasource.FavoriteDataSource
import com.arttrip.app.data.remote.mapper.base.toAppError
import com.arttrip.app.data.remote.paging.favorite.FavoritePagingSource
import com.arttrip.app.domain.model.favorite.Bookmark
import com.arttrip.app.domain.model.favorite.BookmarkSortType
import com.arttrip.app.domain.model.network.ApiResult
import com.arttrip.app.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class BookmarkRepositoryImpl
    @Inject
    constructor(
        private val dataSource: FavoriteDataSource,
    ) : BookmarkRepository {
        private val _bookmarkTotalCount = MutableStateFlow<Int?>(null)
        override val bookmarkTotalCount: StateFlow<Int?> = _bookmarkTotalCount.asStateFlow()

        override fun addBookmark(exhibitId: Int): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    dataSource.postFavorite(
                        exhibitId,
                    )

                    emit(ApiResult.Success(Unit))
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun removeBookmark(exhibitId: Int): Flow<ApiResult<Unit>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    dataSource.deleteFavorite(
                        exhibitId,
                    )
                    emit(ApiResult.Success(Unit))
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getBookmarks(
            pageSize: Int,
            initialLoadSize: Int,
            sortType: BookmarkSortType,
            regions: List<String>?,
            countries: List<String>?,
            cursor: Int?,
        ): Flow<PagingData<Bookmark>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = pageSize,
                        initialLoadSize = initialLoadSize,
                        prefetchDistance = 1,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    FavoritePagingSource(
                        dataSource = dataSource,
                        sortOption = sortType,
                        regions = regions,
                        countries = countries,
                        onTotalCount = { count ->
                            _bookmarkTotalCount.value = count
                        },
                    )
                },
            ).flow

        override fun clearBookmarkTotalCount() {
            _bookmarkTotalCount.value = null
        }
    }
