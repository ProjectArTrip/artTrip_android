package com.arttrip.app.data.remote.mapper.review

import com.arttrip.app.data.remote.model.review.ExhibitReviewResDto
import com.arttrip.app.domain.model.review.ExhibitionReview

fun ExhibitReviewResDto.toDomain(): ExhibitionReview =
    ExhibitionReview(
        id = reviewId,
        reviewer = reviewer ?: "사용자",
        visitDate = visitDate,
        content = content,
        photoUrls = photoUrls.orEmpty(),
    )
