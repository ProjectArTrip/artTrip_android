package com.arttrip.app.core.navigation.main

import com.arttrip.app.core.model.enums.domestic.DomesticRegion
import com.arttrip.app.core.model.enums.exhibition.ExhibitionGenre
import com.arttrip.app.core.model.enums.foreign.ForeignCountry
import java.time.LocalDate

object MainRoute {
    const val HOME = "home"
    const val MAP = "map"
    const val STAMP = "stamp"
    const val BOOKMARK = "bookmark"
    const val MY_PAGE = "my_page"

    private const val HOME_DATE_FILTER_RESULT_ROUTE = "home_date_filter_result"
    const val HOME_DATE_FILTER_RESULT =
        "$HOME_DATE_FILTER_RESULT_ROUTE?isDomestic={isDomestic}&location={location}&startDate={startDate}&endDate={endDate}"

    fun dateFilterResult(
        isDomestic: Boolean,
        location: String,
        startDate: LocalDate,
        endDate: LocalDate,
    ): String = "$HOME_DATE_FILTER_RESULT_ROUTE?isDomestic=$isDomestic&location=$location&startDate=$startDate&endDate=$endDate"

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

    private const val HOME_CURATION_ROUTE = "home_curation"
    const val HOME_CURATION = "$HOME_CURATION_ROUTE/{curationId}"

    fun curation(curationId: Long) = "$HOME_CURATION_ROUTE/$curationId"

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

    const val MY_PAGE_EDIT_PROFILE = "mypage/edit_profile"
    const val MY_PAGE_RECENT_EXHIBITIONS = "mypage/recent_exhibitions"
    const val MY_PAGE_MY_REVIEWS = "mypage/my_reviews"
    const val MY_PAGE_SETTINGS = "mypage/settings"
    const val MY_PAGE_NOTIFICATION = "mypage/settings/notification"
    const val MY_PAGE_NOTICE = "mypage/settings/notice"
}
