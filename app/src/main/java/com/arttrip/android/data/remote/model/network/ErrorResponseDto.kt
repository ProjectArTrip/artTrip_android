package com.arttrip.android.data.remote.model.network

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val isSuccess: Boolean?,
    val code: String?,
    val message: String?,
)
