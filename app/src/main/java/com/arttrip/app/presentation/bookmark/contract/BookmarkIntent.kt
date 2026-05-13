package com.arttrip.app.presentation.bookmark.contract

import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.foreign.ForeignCountry

sealed interface BookmarkIntent {
    data class ChangeSort(
        val sort: BookmarkSort,
    ) : BookmarkIntent

    data class ClickItem(
        val exhibitId: Int,
    ) : BookmarkIntent

    data class ToggleBookmark(
        val exhibitId: Int,
    ) : BookmarkIntent

    data class SeedBookmark(
        val exhibitId: Int,
    ) : BookmarkIntent

    data object FilterSheetOpened : BookmarkIntent

    data object FilterSheetDismissed : BookmarkIntent

    data class ToggleForeignCountry(
        val country: ForeignCountry,
    ) : BookmarkIntent

    data class ToggleDomesticRegion(
        val region: DomesticRegion,
    ) : BookmarkIntent

    data object ResetFilter : BookmarkIntent

    data object ClickSearch : BookmarkIntent
}
