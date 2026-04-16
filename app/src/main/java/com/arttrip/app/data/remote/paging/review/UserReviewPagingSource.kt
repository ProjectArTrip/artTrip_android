package com.arttrip.app.data.remote.paging.review

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arttrip.app.data.remote.datasource.ReviewDataSource
import com.arttrip.app.data.remote.mapper.review.toDomain
import com.arttrip.app.domain.model.review.UserReview

class UserReviewPagingSource(
    private val dataSource: ReviewDataSource,
    private val onTotalCount: (Int) -> Unit,
) : PagingSource<Int, UserReview>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserReview> =
        try {
            val cursor: Int? = params.key
            val loadSize: Int = params.loadSize

            Log.d("ReviewPagingSource", "load() cursor=$cursor loadSize=$loadSize")
            val res =
                dataSource.getUserReviews(
                    cursor = cursor,
                    size = loadSize,
                )

            if (cursor == null) {
                onTotalCount(res.reviewTotalCount)
            }
            val items: List<UserReview> =
                res.reviews.map { it.toDomain() }
            Log.d("UserReviewPagingSource", "mapped items size=${items.size}")
            Log.d("UserReviewPagingSource", "mapped items=$items")

            val nextKey =
                if (res.hasNext) res.nextCursor else null

            LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (e: Throwable) {
            Log.e("UserReviewPagingSource", "load error", e)
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, UserReview>): Int? {
        // 새로고침은 첫 페이지부터 다시 (cursor=null)
        return null
    }
}
