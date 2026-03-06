package com.arttrip.android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arttrip.android.data.remote.datasource.ReviewDataSource
import com.arttrip.android.data.remote.paging.review.ExhibitReviewPagingSource
import com.arttrip.android.domain.model.review.Review
import com.arttrip.android.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ReviewRepositoryImpl
    @Inject
    constructor(
        private val reviewDataSource: ReviewDataSource,
    ) : ReviewRepository {
        private val _exhibitReviewTotalCount = MutableStateFlow<Int?>(null)
        override val exhibitReviewTotalCount: StateFlow<Int?> = _exhibitReviewTotalCount.asStateFlow()

        override fun clearExhibitReviewTotalCount() {
            _exhibitReviewTotalCount.value = null
        }

        override fun getExhibitionReviews(
            exhibitId: Int,
            pageSize: Int,
            initialLoadSize: Int,
        ): Flow<PagingData<Review>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = pageSize,
                        initialLoadSize = initialLoadSize,
                        prefetchDistance = 1,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    ExhibitReviewPagingSource(
                        dataSource = reviewDataSource,
                        exhibitId = exhibitId,
                        onTotalCount = { count ->
                            _exhibitReviewTotalCount.value = count
                        },
                    )
                },
            ).flow
    }
