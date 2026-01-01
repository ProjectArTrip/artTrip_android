package com.arttrip.android.presentation.exhibition.contract

import com.arttrip.android.domain.model.exhibition.ExhibitionDetailModel

data class ExhibitionDetailState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val detail: ExhibitionDetailModel? = null,
    val isBookmarked: Boolean = false,
    val isBookmarkSyncing: Boolean = false,
    val reviewTotalCount: Int? = null,
    val writeReviewDialogVisible: Boolean = false,
)
