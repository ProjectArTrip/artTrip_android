package com.arttrip.app.data.remote.datasource

import com.arttrip.app.core.model.image.ImageQueryParams
import com.arttrip.app.data.remote.api.UserApi
import com.arttrip.app.data.remote.model.user.UserFcmTokenReqDto
import com.arttrip.app.data.remote.model.user.UserNicknameReqDto
import com.arttrip.app.data.remote.model.user.UserPushEnabledReqDto
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

        suspend fun postUserFcmToken(userFcmTokenReqDto: UserFcmTokenReqDto) = api.postFcmToken(userFcmTokenReqDto)

        suspend fun patchPushEnabled(userPushEnabledReqDto: UserPushEnabledReqDto) = api.patchPushEnabled(userPushEnabledReqDto)
    }
