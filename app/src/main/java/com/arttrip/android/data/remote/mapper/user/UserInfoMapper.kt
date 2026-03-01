package com.arttrip.android.data.remote.mapper.user

import com.arttrip.android.data.remote.model.user.UserInfoResDto
import com.arttrip.android.domain.model.profile.UserProfile

fun UserInfoResDto.toDomain(): UserProfile =
    UserProfile(
        nickname = nickName,
        profileImageUrl = profileImage,
        email = email,
    )
