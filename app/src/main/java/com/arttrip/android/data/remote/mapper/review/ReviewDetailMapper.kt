package com.arttrip.android.data.remote.mapper.review

import com.arttrip.android.data.remote.model.review.ReviewDetailResDto
import com.arttrip.android.data.remote.model.review.ReviewImageResDto
import com.arttrip.android.domain.model.review.ReviewDetail
import com.arttrip.android.domain.model.review.ReviewImage

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
