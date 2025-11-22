package com.arttrip.android.data.remote.model.network

data class BaseResponseDto<T>(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: T?,
)
