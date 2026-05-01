package com.arttrip.app.data.remote.paging.curation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arttrip.app.data.remote.datasource.CurationDataSource
import com.arttrip.app.data.remote.mapper.home.toDomain
import com.arttrip.app.domain.model.exhibition.Exhibition
import okio.IOException
import retrofit2.HttpException

class CurationExhibitPagingSource(
    private val dataSource: CurationDataSource,
    private val curationId: Long,
    private val onTitleLoaded: (String) -> Unit = {},
) : PagingSource<Int, Exhibition>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Exhibition> =
        try {
            val cursor: Int? = params.key
            val loadSize: Int = params.loadSize

            val res = dataSource.getCurationExhibits(
                curationId = curationId,
                cursor = cursor,
                size = loadSize,
            )

            if (cursor == null) {
                onTitleLoaded(res.title)
            }

            val items: List<Exhibition> = res.exhibits.map { it.toDomain() }
            val nextKey = if (res.hasNext) res.nextCursor else null

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

    override fun getRefreshKey(state: PagingState<Int, Exhibition>): Int? = null
}
