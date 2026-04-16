package com.arttrip.app.data.remote.mapper.user

import com.arttrip.app.data.remote.model.user.UserRecentExhibitsResDto
import com.arttrip.app.domain.model.exhibition.RecentExhibition

fun UserRecentExhibitsResDto.toDomain(): List<RecentExhibition> =
    exhibits.map { dto ->
        RecentExhibition(
            exhibitId = dto.exhibitId,
            title = dto.title,
            hallName = dto.exhibitHallName,
            posterUrl = dto.exhibitImage,
        )
    }
