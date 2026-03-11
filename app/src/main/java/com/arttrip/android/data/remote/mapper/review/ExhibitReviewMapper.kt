package com.arttrip.android.data.remote.mapper.review

import com.arttrip.android.data.remote.model.review.ExhibitReviewDto
import com.arttrip.android.domain.model.review.ExhibitionReview

fun ExhibitReviewDto.toDomain(): ExhibitionReview =
    ExhibitionReview(
        id = reviewId,
        reviewer = reviewer ?: "사용자",
        visitDate = visitDate,
        content = content,
        photoUrls = photoUrls.orEmpty(),
    )
