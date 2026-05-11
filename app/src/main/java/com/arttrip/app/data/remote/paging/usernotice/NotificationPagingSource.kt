package com.arttrip.app.data.remote.paging.usernotice

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arttrip.app.data.remote.datasource.UserNoticeDataSource
import com.arttrip.app.data.remote.mapper.usernotice.toNotification
import com.arttrip.app.domain.model.notification.Notification
import retrofit2.HttpException
import java.io.IOException

class NotificationPagingSource(
    private val dataSource: UserNoticeDataSource,
) : PagingSource<Int, Notification>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> =
        try {
            val res =
                dataSource.getNotices(
                    action = null,
                    cursor = params.key,
                    size = params.loadSize,
                )
            val items = res.notifications.map { it.toNotification() }
            val nextKey = if (res.hasNext) res.nextCursor else null
            LoadResult.Page(data = items, prevKey = null, nextKey = nextKey)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? = null
}
