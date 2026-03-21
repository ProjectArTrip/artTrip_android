package com.arttrip.android.data.remote.paging.exhibit

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.data.remote.datasource.ExhibitDataSource
import com.arttrip.android.data.remote.mapper.exhibit.toDomain
import com.arttrip.android.domain.model.exhibition.Exhibition
import retrofit2.HttpException
import java.io.IOException

class ExhibitListPagingSource(
    private val dataSource: ExhibitDataSource,
    private val query: String?,
    private val startDate: String?,
    private val endDate: String?,
    private val isDomestic: Boolean?,
    private val country: ForeignCountry?,
    private val region: DomesticRegion?,
    private val genres: List<String>?,
    private val styles: List<String>?,
    private val sortType: SortType?,
    private val onTotalCountLoaded: (Int) -> Unit = {},
) : PagingSource<Int, Exhibition>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Exhibition> =
        try {
            val cursor: Int? = params.key
            val loadSize: Int = params.loadSize

            val res = dataSource.getExhibits(
                cursor = cursor,
                size = loadSize,
                query = query,
                startDate = startDate,
                endDate = endDate,
                isDomestic = isDomestic,
                country = country,
                region = region,
                genres = genres,
                styles = styles,
                sortType = sortType,
            )

            onTotalCountLoaded(res.exhibitTotalCount)

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
