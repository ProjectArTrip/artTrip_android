package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.HomeApi
import javax.inject.Inject

class HomeDataSource
    @Inject
    constructor(
        private val api: HomeApi,
    ) {
        suspend fun getCountryList() = api.getCountryList()
    }
