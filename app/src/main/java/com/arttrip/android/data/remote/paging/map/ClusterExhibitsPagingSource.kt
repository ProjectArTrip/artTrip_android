package com.arttrip.android.data.remote.paging.map

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arttrip.android.data.remote.datasource.MapDataSource
import com.arttrip.android.data.remote.mapper.exhibit.toDomain
import com.arttrip.android.domain.model.exhibition.Exhibition
import retrofit2.HttpException
import java.io.IOException

class ClusterExhibitsPagingSource(
    private val ids: List<Int>,
    private val dataSource: MapDataSource,
) : PagingSource<Int, Exhibition>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Exhibition> =
        try {
            val res =
                dataSource.getClusterExhibits(
                    ids = ids,
                    cursor = params.key,
                    size = params.loadSize,
                )
            LoadResult.Page(
                data = res.exhibits.map { it.toDomain() },
                prevKey = null,
                nextKey = if (res.hasNext) res.nextCursor else null,
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, Exhibition>): Int? = null
}
