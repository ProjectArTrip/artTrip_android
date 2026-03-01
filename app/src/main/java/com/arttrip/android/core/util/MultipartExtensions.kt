package com.arttrip.android.core.util

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun File.toMultipartPart(
    fieldName: String,
    contentType: MediaType = "image/*".toMediaType(),
): MultipartBody.Part {
    val body = asRequestBody(contentType)
    return MultipartBody.Part.createFormData(
        name = fieldName,
        filename = name,
        body = body,
    )
}
