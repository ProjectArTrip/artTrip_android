package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.ReviewApi
import javax.inject.Inject

class ReviewDataSource
    @Inject
    constructor(
        private val api: ReviewApi,
    ) {
        suspend fun getExhibitDetailReviews(
            exhibitId: Int,
            cursor: Int?,
            size: Int,
        ) = api.getExhibitDetailReviews(
            exhibitId = exhibitId,
            cursor = cursor,
            size = size,
        )
    }
