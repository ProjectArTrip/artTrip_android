package com.arttrip.android.data.remote.mapper.review

import com.arttrip.android.data.remote.model.review.ReviewDetailDto
import com.arttrip.android.domain.model.review.ReviewModel

fun ReviewDetailDto.toDomain(): ReviewModel =
    ReviewModel(
        id = reviewId,
        writer = nickname ?: "nickname",
        visitDate = visitDate,
        content = content,
        photoUrls =
            thumbnailUrl
                ?.let { url -> List(4) { url } }
                ?: emptyList(),
    )
