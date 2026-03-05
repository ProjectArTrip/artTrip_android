package com.arttrip.android.data.remote.datasource

import com.arttrip.android.core.model.image.ImageQueryParams
import com.arttrip.android.data.remote.api.UserApi
import com.arttrip.android.data.remote.model.user.UserNicknameReqDto
import okhttp3.MultipartBody
import javax.inject.Inject

class UserDataSource
    @Inject
    constructor(
        private val api: UserApi,
    ) {
        suspend fun getUserInfo(image: ImageQueryParams) =
            api.getUserInfo(
                w = image.widthPx,
                h = image.heightPx,
                f = image.format.value,
            )

        suspend fun patchUserNickname(userNicknameReqDto: UserNicknameReqDto) = api.patchUserNickname(userNicknameReqDto)

        suspend fun deleteProfileImage() = api.deleteProfileImage()

        suspend fun patchProfileImage(part: MultipartBody.Part) = api.patchProfileImage(part)

        suspend fun getUserRecentExhibits(image: ImageQueryParams) =
            api.getUserRecentExhibits(
                w = image.widthPx,
                h = image.heightPx,
                f = image.format.value,
            )
    }
