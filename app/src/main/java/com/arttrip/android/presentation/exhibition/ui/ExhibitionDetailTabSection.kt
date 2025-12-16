package com.arttrip.android.presentation.exhibition.ui

import androidx.compose.runtime.Composable
import com.arttrip.android.presentation.exhibition.ui.tab.ExhibitionDetailInfoTab
import com.arttrip.android.presentation.exhibition.ui.tab.ExhibitionMapTab
import com.arttrip.android.presentation.exhibition.ui.tab.ExhibitionReviewTab

@Composable
fun ExhibitionDetailTabSection(selectedTabIndex: Int) {
    when (selectedTabIndex) {
        0 -> ExhibitionDetailInfoTab()
        1 -> ExhibitionMapTab()
        2 -> ExhibitionReviewTab()
    }
}
