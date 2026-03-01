package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.UserApi
import okhttp3.MultipartBody
import javax.inject.Inject

class UserDataSource
    @Inject
    constructor(
        private val api: UserApi,
    ) {
        suspend fun getUserInfo() = api.getUserInfo()

        suspend fun deleteProfileImage() = api.deleteProfileImage()

        suspend fun patchProfileImage(part: MultipartBody.Part) = api.patchProfileImage(part)

        suspend fun getUserRecentExhibits() = api.getUserRecentExhibits()
    }
