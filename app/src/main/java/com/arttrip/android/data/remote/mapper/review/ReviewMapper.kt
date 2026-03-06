package com.arttrip.android.data.remote.mapper.review

import com.arttrip.android.data.remote.model.review.ReviewDto
import com.arttrip.android.domain.model.review.Review

fun ReviewDto.toDomain(): Review =
    Review(
        id = reviewId,
        writer = nickname ?: "nickname",
        visitDate = visitDate,
        content = content,
        photoUrls =
            thumbnailUrl
                ?.let { url -> List(4) { url } }
                ?: emptyList(),
    )
