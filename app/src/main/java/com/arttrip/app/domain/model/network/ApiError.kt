package com.arttrip.app.domain.model.network

sealed class ApiError {
    object NetworkError : ApiError()

    data class HttpError(
        val statusCode: Int,
        val serverCode: String? = null,
        val serverMessage: String? = null,
    ) : ApiError()

    data class Unknown(
        val throwable: Throwable,
    ) : ApiError()
}
