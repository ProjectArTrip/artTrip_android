package com.arttrip.app.presentation.exhibition.contract

import com.arttrip.app.core.model.image.ImageQueryParams

sealed interface ExhibitionDetailIntent {
    data class Initialize(
        val exhibitId: Int,
        val imageQueryParams: ImageQueryParams,
    ) : ExhibitionDetailIntent

    data object BackClicked : ExhibitionDetailIntent

    data object BookmarkClicked : ExhibitionDetailIntent

    data object WriteReviewClicked : ExhibitionDetailIntent

    data object WriteReviewDialogDismissClicked : ExhibitionDetailIntent

    data object WriteReviewConfirmClicked : ExhibitionDetailIntent

    data object OnReviewWriteSuccess : ExhibitionDetailIntent
}
