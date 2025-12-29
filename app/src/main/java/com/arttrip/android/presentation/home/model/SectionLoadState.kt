package com.arttrip.android.presentation.home.model

import com.arttrip.android.domain.model.network.ApiError

sealed interface SectionLoadState<out T> {
    object Idle : SectionLoadState<Nothing>
    object Loading : SectionLoadState<Nothing>
    data class Success<T>(val data: T) : SectionLoadState<T>
    data class Error(val error: ApiError) : SectionLoadState<Nothing>
}