package com.arttrip.app.data.remote.mapper.user

import com.arttrip.app.data.remote.model.user.UserResDto
import com.arttrip.app.domain.model.profile.UserProfile

fun UserResDto.toDomain(): UserProfile =
    UserProfile(
        nickname = nickName ?: "사용자",
        profileImageUrl = profileImage,
        email = email,
    )
