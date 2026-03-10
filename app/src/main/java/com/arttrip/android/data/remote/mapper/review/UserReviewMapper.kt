package com.arttrip.android.data.remote.mapper.review

import com.arttrip.android.data.remote.model.review.UserReviewDto
import com.arttrip.android.domain.model.review.UserReview

fun UserReviewDto.toDomain(): UserReview =
    UserReview(
        id = reviewId,
        exhibitionTitle = reviewTitle,
        visitedDate = visitDate,
        content = content,
        photoUrls = photoUrls.orEmpty(),
        posterUrl = posterUrl,
    )
