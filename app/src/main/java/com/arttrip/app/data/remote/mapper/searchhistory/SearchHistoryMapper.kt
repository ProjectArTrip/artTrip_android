package com.arttrip.app.data.remote.mapper.searchhistory

import com.arttrip.app.data.remote.model.searchhistory.SearchHistoryResponseDto
import com.arttrip.app.domain.model.recentsearch.RecentSearch

fun SearchHistoryResponseDto.toDomain(): List<RecentSearch> =
    items.map { dto ->
        RecentSearch(
            id = dto.searchHistoryId,
            content = dto.content,
        )
    }
