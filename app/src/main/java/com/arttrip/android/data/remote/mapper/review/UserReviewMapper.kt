package com.arttrip.android.data.remote.mapper.review

import com.arttrip.android.data.remote.model.review.UserReviewResDto
import com.arttrip.android.domain.model.review.UserReview

fun UserReviewResDto.toDomain(): UserReview =
    UserReview(
        id = reviewId,
        exhibitionTitle = reviewTitle,
        visitedDate = visitDate,
        content = content,
        photoUrls = photoUrls.orEmpty(),
        hallName = hallName,
        posterUrl = posterUrl,
    )
