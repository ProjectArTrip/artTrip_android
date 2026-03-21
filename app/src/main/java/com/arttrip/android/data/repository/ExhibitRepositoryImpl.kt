package com.arttrip.android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.SortType
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.data.remote.datasource.ExhibitDataSource
import com.arttrip.android.data.remote.datasource.UserDataSource
import com.arttrip.android.data.remote.mapper.base.toAppError
import com.arttrip.android.data.remote.mapper.exhibit.toDomain
import com.arttrip.android.data.remote.mapper.user.toDomain
import com.arttrip.android.data.remote.paging.exhibit.ExhibitListPagingSource
import com.arttrip.android.domain.model.exhibition.Exhibition
import com.arttrip.android.domain.model.exhibition.ExhibitionDetail
import com.arttrip.android.domain.model.exhibition.RecentExhibition
import com.arttrip.android.domain.model.network.ApiResult
import com.arttrip.android.domain.repository.ExhibitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExhibitRepositoryImpl
    @Inject
    constructor(
        private val exhibitDataSource: ExhibitDataSource,
        private val userDataSource: UserDataSource,
    ) : ExhibitRepository {
        override fun getExhibitDetail(
            exhibitId: Int,
            imageQueryParams: ImageQueryParams,
        ): Flow<ApiResult<ExhibitionDetail>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val dto =
                        exhibitDataSource.getExhibitDetail(exhibitId, imageQueryParams)
                    val exhibitDetail: ExhibitionDetail = dto.toDomain()

                    emit(ApiResult.Success(exhibitDetail))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }

        override fun getUserRecentExhibits(imageQueryParams: ImageQueryParams): Flow<ApiResult<List<RecentExhibition>>> =
            flow {
                emit(ApiResult.Loading)

                try {
                    val dto =
                        userDataSource.getUserRecentExhibits(imageQueryParams)
                    val exhibits = dto.toDomain()

                    emit(ApiResult.Success(exhibits))
                } catch (e: Exception) {
                    val error = e.toAppError()
                    emit(ApiResult.Error(error))
                }
            }
        override fun getExhibits(
            query: String?,
            startDate: String?,
            endDate: String?,
            isDomestic: Boolean?,
            country: ForeignCountry?,
            region: DomesticRegion?,
            genres: List<String>?,
            styles: List<String>?,
            sortType: SortType?,
            pageSize: Int,
            initialLoadSize: Int,
            onTotalCountLoaded: (Int) -> Unit,
        ): Flow<PagingData<Exhibition>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = pageSize,
                        initialLoadSize = initialLoadSize,
                        prefetchDistance = 1,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    ExhibitListPagingSource(
                        dataSource = dataSource,
                        query = query,
                        startDate = startDate,
                        endDate = endDate,
                        isDomestic = isDomestic,
                        country = country,
                        region = region,
                        genres = genres,
                        styles = styles,
                        sortType = sortType,
                        onTotalCountLoaded = onTotalCountLoaded,
                    )
                },
            ).flow
    }
