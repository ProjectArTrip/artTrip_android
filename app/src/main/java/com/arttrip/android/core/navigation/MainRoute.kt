package com.arttrip.android.core.navigation

object MainRoute {
    const val HOME = "home"
    const val MAP = "map"
    const val STAMP = "stamp"
    const val BOOKMARK = "bookmark"
    const val MY_PAGE = "my_page"

    const val HOME_DATE_FILTER = "home_date_filter"

    private const val EXHIBITION_DETAIL_ROUTE = "exhibitionDetail"
    const val EXHIBITION_DETAIL = "$EXHIBITION_DETAIL_ROUTE/{exhibitId}"

    fun exhibitionDetail(exhibitId: Int) = "$EXHIBITION_DETAIL_ROUTE/$exhibitId"
}
