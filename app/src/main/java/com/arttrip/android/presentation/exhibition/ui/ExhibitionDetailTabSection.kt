package com.arttrip.android.presentation.exhibition.ui

import androidx.compose.runtime.Composable
import com.arttrip.android.domain.model.exhibit.ExhibitionDetailModel
import com.arttrip.android.presentation.exhibition.ui.tab.ExhibitionDetailInfoTab
import com.arttrip.android.presentation.exhibition.ui.tab.ExhibitionMapTab
import com.arttrip.android.presentation.exhibition.ui.tab.ExhibitionReviewTab

@Composable
fun ExhibitionDetailTabSection(
    selectedTabIndex: Int,
    detail: ExhibitionDetailModel,
) {
    when (selectedTabIndex) {
        0 -> ExhibitionDetailInfoTab(detail = detail)
        1 -> ExhibitionMapTab(detail = detail)
        2 -> ExhibitionReviewTab(detail = detail)
    }
}
