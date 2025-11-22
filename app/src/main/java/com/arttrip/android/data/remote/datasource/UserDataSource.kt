package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.UserApi
import javax.inject.Inject

class UserDataSource
    @Inject
    constructor(
        private val api: UserApi,
    ) {
        suspend fun getUserInfo(id: Int) = api.getUserInfo(id)
    }
