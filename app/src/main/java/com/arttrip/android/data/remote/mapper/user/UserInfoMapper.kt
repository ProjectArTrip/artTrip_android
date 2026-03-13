package com.arttrip.android.data.remote.mapper.user

import com.arttrip.android.data.remote.model.user.UserResDto
import com.arttrip.android.domain.model.profile.UserProfile

fun UserResDto.toDomain(): UserProfile =
    UserProfile(
        nickname = nickName ?: "사용자",
        profileImageUrl = profileImage,
        email = email,
    )
