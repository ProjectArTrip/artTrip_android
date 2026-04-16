package com.arttrip.app.data.remote.mapper.review

import com.arttrip.app.data.remote.model.review.ReviewDetailResDto
import com.arttrip.app.data.remote.model.review.ReviewImageResDto
import com.arttrip.app.domain.model.review.ReviewDetail
import com.arttrip.app.domain.model.review.ReviewImage

fun ReviewImageResDto.toDomain(): ReviewImage =
    ReviewImage(
        imageId = reviewImageId,
        imageUrl = imageUrl,
    )

fun ReviewDetailResDto.toDomain(): ReviewDetail =
    ReviewDetail(
        reviewId = reviewId,
        visitDate = date,
        content = content,
        images = images?.map { it.toDomain() } ?: emptyList(),
    )
