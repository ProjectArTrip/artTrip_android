package com.arttrip.android.data.remote.mapper.review

import com.arttrip.android.data.remote.model.review.CreateReviewReqDto
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun CreateReviewReqDto.toRequestBody(): RequestBody =
    Json
        .encodeToString(this)
        .toRequestBody("application/json".toMediaType())
