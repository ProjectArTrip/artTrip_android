package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.HomeApi
import javax.inject.Inject

class HomeDataSource
    @Inject
    constructor(
        private val api: HomeApi,
    ) {
        suspend fun getCountryList() = api.getCountryList()

        suspend fun getHomeRecommendToday(isDomestic: Boolean) = api.getHomeRecommendToday(isDomestic = isDomestic)
        suspend fun getHomePersonalized(isDomestic: Boolean) = api.getHomePersonalized(isDomestic = isDomestic)

        suspend fun getHomeSchedule(isDomestic: Boolean, date: String) = api.getHomeSchedule(isDomestic = isDomestic, date = date)
    }
