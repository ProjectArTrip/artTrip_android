package com.arttrip.android.core.navigation.main

import com.arttrip.android.core.model.enums.domestic.DomesticRegion
import com.arttrip.android.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.android.core.model.enums.foreign.ForeignCountry
import java.time.LocalDate

object MainRoute {
    const val HOME = "home"
    const val MAP = "map"
    const val STAMP = "stamp"
    const val BOOKMARK = "bookmark"
    const val MY_PAGE = "my_page"

    const val HOME_DATE_RESULT = "home_date_result"

    const val HOME_NOTIFICATION = "home_notification"

    const val HOME_SEARCH = "home_search"

    private const val HOME_REGION_ROUTE = "home_region"
    const val HOME_REGION = "$HOME_REGION_ROUTE/{region}"

    fun region(region: DomesticRegion) = "$HOME_REGION_ROUTE/${region.name}"

    private const val HOME_SCHEDULE_ROUTE = "home_schedule"
    const val HOME_SCHEDULE = "$HOME_SCHEDULE_ROUTE?country={country}&date={date}"

    fun schedule(
        country: ForeignCountry? = null,
        date: LocalDate,
    ): String =
        if (country == null) {
            "$HOME_SCHEDULE_ROUTE?date=$date"
        } else {
            "$HOME_SCHEDULE_ROUTE?country=${country.name}&date=$date"
        }

    private const val HOME_GENRE_ROUTE = "home_genre"
    const val HOME_GENRE = "$HOME_GENRE_ROUTE?country={country}&genre={genre}"

    fun genre(
        country: ForeignCountry? = null,
        genre: ExhibitionGenre,
    ): String =
        if (country == null) {
            "$HOME_GENRE_ROUTE?genre=${genre.name}"
        } else {
            "$HOME_GENRE_ROUTE?country=${country.name}&genre=${genre.name}"
        }

    private const val EXHIBITION_DETAIL_ROUTE = "exhibition_detail"
    const val EXHIBITION_DETAIL = "$EXHIBITION_DETAIL_ROUTE/{exhibitId}"

    fun exhibitionDetail(exhibitId: Int) = "$EXHIBITION_DETAIL_ROUTE/$exhibitId"

    const val REVIEW_WRITE = "review_write"

    const val TASTE_ANALYSIS = "mypage/taste_analysis"
}
