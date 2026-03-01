package com.arttrip.android.data.remote.mapper.user

import com.arttrip.android.data.remote.model.user.UserRecentExhibitsResDto
import com.arttrip.android.domain.model.exhibition.RecentExhibition

fun UserRecentExhibitsResDto.toDomain(): List<RecentExhibition> =
    exhibits.map { dto ->
        RecentExhibition(
            exhibitId = dto.exhibitId,
            title = dto.title,
            hallName = dto.exhibitHallName,
        )
    }
