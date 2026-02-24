package com.arttrip.android.data.remote.model.network

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class ErrorResponseDto(
    val code: String? = null,
    val message: String? = null,
)
