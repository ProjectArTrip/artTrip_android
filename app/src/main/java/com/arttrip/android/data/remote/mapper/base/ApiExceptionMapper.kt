package com.arttrip.android.data.remote.mapper.base

import com.arttrip.android.data.remote.model.network.ErrorResponseDto
import com.arttrip.android.domain.model.network.ApiError
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

private val errorJson =
    Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

fun Throwable.toAppError(): ApiError =
    when (this) {
        is IOException -> ApiError.NetworkError
        is HttpException -> {
            val status = code()
            val errorBody = response()?.errorBody()?.string()

            val dto: ErrorResponseDto? =
                errorBody
                    ?.takeIf { it.isNotBlank() }
                    ?.let { body ->
                        runCatching { errorJson.decodeFromString(ErrorResponseDto.serializer(), body) }
                            .getOrNull()
                    }

            ApiError.HttpError(
                statusCode = status,
                serverCode = dto?.code,
                serverMessage = dto?.message,
            )
        }
        else -> ApiError.Unknown(this)
    }
