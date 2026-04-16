package com.arttrip.app.data.remote.paging.review
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arttrip.app.data.remote.datasource.ReviewDataSource
import com.arttrip.app.data.remote.mapper.review.toDomain
import com.arttrip.app.domain.model.review.ExhibitionReview
import retrofit2.HttpException
import java.io.IOException

class ExhibitReviewPagingSource(
    private val dataSource: ReviewDataSource,
    private val exhibitId: Int,
    private val onTotalCount: (Int) -> Unit,
) : PagingSource<Int, ExhibitionReview>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ExhibitionReview> =
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
            val items: List<ExhibitionReview> =
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

    override fun getRefreshKey(state: PagingState<Int, ExhibitionReview>): Int? {
        // 새로고침은 첫 페이지부터 다시 (cursor=null)
        return null
    }
}
