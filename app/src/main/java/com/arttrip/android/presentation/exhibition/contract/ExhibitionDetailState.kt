package com.arttrip.android.presentation.exhibition.contract

import com.arttrip.android.domain.model.exhibit.ExhibitionDetailModel

data class ExhibitionDetailState(
    val detail: ExhibitionDetailModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
