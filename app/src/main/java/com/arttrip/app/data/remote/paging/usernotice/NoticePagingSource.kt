package com.arttrip.app.data.remote.paging.usernotice

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arttrip.app.data.remote.datasource.UserNoticeDataSource
import com.arttrip.app.data.remote.mapper.review.toDomain
import com.arttrip.app.data.remote.mapper.usernotice.toDomain
import com.arttrip.app.domain.model.notice.Notice
import retrofit2.HttpException
import java.io.IOException

class NoticePagingSource(
    private val dataSource: UserNoticeDataSource,
) : PagingSource<Int, Notice>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notice> =
        try {
            val cursor: Int? = params.key
            val loadSize: Int = params.loadSize

            Log.d("NoticePagingSource", "load() cursor=$cursor loadSize=$loadSize")
            val res =
                dataSource.getNotices(
                    cursor = cursor,
                    size = loadSize,
                )

            val items: List<Notice> =
                res.notifications.map { it.toDomain() }

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

    override fun getRefreshKey(state: PagingState<Int, Notice>): Int? = null
}
