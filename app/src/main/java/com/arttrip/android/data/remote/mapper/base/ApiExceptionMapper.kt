package com.arttrip.android.data.remote.mapper.base

import com.arttrip.android.data.remote.model.network.ErrorResponseDto
import com.arttrip.android.domain.model.network.ApiError
import kotlinx.serialization.json.Json.Default.decodeFromString
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toAppError(): ApiError =
    when (this) {
        is IOException -> ApiError.NetworkError
        is HttpException -> {
            val status = code()
            val errorBody = response()?.errorBody()?.string()
            val errorDto =
                if (errorBody != null) {
                    decodeFromString<ErrorResponseDto>(errorBody)
                } else {
                    ErrorResponseDto(isSuccess = null, code = null, message = null)
                }

            ApiError.HttpError(
                statusCode = status,
                serverCode = errorDto.code,
                serverMessage = errorDto.message,
            )
        }
        else -> ApiError.Unknown(this)
    }
