package com.arttrip.app.data.remote.mapper.review

import com.arttrip.app.data.remote.model.review.UserReviewResDto
import com.arttrip.app.domain.model.review.UserReview

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
