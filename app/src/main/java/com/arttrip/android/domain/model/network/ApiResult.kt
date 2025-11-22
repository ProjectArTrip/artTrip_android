package com.arttrip.android.domain.model.network

sealed class ApiResult<out T> {
    object Loading : ApiResult<Nothing>()

    data class Success<T>(
        val data: T,
    ) : ApiResult<T>()

    data class Error(
        val error: ApiError,
    ) : ApiResult<Nothing>()
}
