package com.arttrip.android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arttrip.android.data.remote.datasource.ReviewDataSource
import com.arttrip.android.data.remote.paging.review.ReviewPagingSource
import com.arttrip.android.domain.model.review.ReviewModel
import com.arttrip.android.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRepositoryImpl
    @Inject
    constructor(
        private val reviewDataSource: ReviewDataSource,
    ) : ReviewRepository {
        override fun getExhibitionReviews(
            exhibitId: Int,
            pageSize: Int,
            initialLoadSize: Int,
        ): Flow<PagingData<ReviewModel>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = pageSize,
                        initialLoadSize = initialLoadSize,
                        prefetchDistance = 1,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    ReviewPagingSource(
                        dataSource = reviewDataSource,
                        exhibitId = exhibitId,
                    )
                },
            ).flow
    }
