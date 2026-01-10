package com.arttrip.android.domain.repository

import androidx.paging.PagingData
import com.arttrip.android.domain.model.review.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ReviewRepository {
    val reviewTotalCount: StateFlow<Int?>

    fun getExhibitionReviews(
        exhibitId: Int,
        pageSize: Int = 10,
        initialLoadSize: Int = 10,
    ): Flow<PagingData<Review>>

    fun clearReviewTotalCount()
}
