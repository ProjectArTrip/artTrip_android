package com.arttrip.android.data.remote.mapper.user

import com.arttrip.android.data.remote.model.user.UserInfoDto
import com.arttrip.android.domain.model.user.UserInfoModel

fun UserInfoDto.toDomain(): UserInfoModel =
    UserInfoModel(
        id = id,
        name = name,
    )
