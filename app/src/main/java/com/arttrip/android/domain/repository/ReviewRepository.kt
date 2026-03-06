package com.arttrip.android.domain.repository

import androidx.paging.PagingData
import com.arttrip.android.domain.model.review.ExhibitionReview
import com.arttrip.android.domain.model.review.UserReview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ReviewRepository {
    val exhibitReviewTotalCount: StateFlow<Int?>
    val userReviewTotalCount: StateFlow<Int?>

    fun getExhibitionReviews(
        exhibitId: Int,
        pageSize: Int = 10,
        initialLoadSize: Int = 10,
    ): Flow<PagingData<ExhibitionReview>>

    fun getUserReviews(
        pageSize: Int = 10,
        initialLoadSize: Int = 10,
    ): Flow<PagingData<UserReview>>

    fun clearExhibitReviewTotalCount()

    fun clearUserReviewTotalCount()
}
