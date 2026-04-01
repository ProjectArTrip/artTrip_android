package com.arttrip.android.data.remote.paging.favorite

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arttrip.android.data.remote.datasource.FavoriteDataSource
import com.arttrip.android.data.remote.mapper.favorite.toDomain
import com.arttrip.android.domain.model.favorite.Bookmark
import com.arttrip.android.domain.model.favorite.BookmarkSortType
import okio.IOException
import retrofit2.HttpException

class FavoritePagingSource(
    private val dataSource: FavoriteDataSource,
    private val sortOption: BookmarkSortType,
    private val regions: List<String>?,
    private val countries: List<String>?,
    private val onTotalCount: (Int) -> Unit,
) : PagingSource<Int, Bookmark>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Bookmark> =
        try {
            val cursor: Int? = params.key
            val loadSize: Int = params.loadSize

            Log.d("FavoritePagingSource", "load() cursor=$cursor loadSize=$loadSize")
            val res =
                dataSource.getFavorites(
                    sortOption = sortOption,
                    regions = regions,
                    countries = countries,
                    cursor = cursor,
                    size = loadSize,
                )

            if (cursor == null) {
                onTotalCount(res.favoriteTotalCount)
            }
            val items: List<Bookmark> =
                res.favorites.map { it.toDomain() }

            val nextKey =
                if (res.hasNext) res.nextCursor else null

            LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, Bookmark>): Int? {
        // 새로고침은 첫 페이지부터 다시 (cursor=null)
        return null
    }
}
