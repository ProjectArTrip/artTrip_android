package com.arttrip.app.data.remote.mapper.review

import com.arttrip.app.data.remote.model.review.CreateReviewReqDto
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun CreateReviewReqDto.toRequestBody(): RequestBody =
    Gson()
        .toJson(this)
        .toRequestBody("application/json".toMediaType())
