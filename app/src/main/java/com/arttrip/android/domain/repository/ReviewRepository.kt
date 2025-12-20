package com.arttrip.android.domain.repository

import androidx.paging.PagingData
import com.arttrip.android.domain.model.review.ReviewModel
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    fun getExhibitionReviews(
        exhibitId: Int,
        pageSize: Int = 10,
        initialLoadSize: Int = 10,
    ): Flow<PagingData<ReviewModel>>
}
