package com.arttrip.android.data.remote.paging.review
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arttrip.android.data.remote.datasource.ReviewDataSource
import com.arttrip.android.data.remote.mapper.review.toDomain
import com.arttrip.android.domain.model.review.Review
import retrofit2.HttpException
import java.io.IOException

class ReviewPagingSource(
    private val dataSource: ReviewDataSource,
    private val exhibitId: Int,
    private val onTotalCount: (Int) -> Unit,
) : PagingSource<Int, Review>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> =
        try {
            val cursor: Int? = params.key
            val loadSize: Int = params.loadSize

            Log.d("ReviewPagingSource", "load() cursor=$cursor loadSize=$loadSize")
            val res =
                dataSource.getExhibitDetailReviews(
                    exhibitId = exhibitId,
                    cursor = cursor,
                    size = loadSize,
                )

            if (cursor == null) {
                onTotalCount(res.reviewTotalCount)
            }
            val items: List<Review> =
                res.reviews.map { it.toDomain() }

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

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        // 새로고침은 첫 페이지부터 다시 (cursor=null)
        return null
    }
}
