package com.arttrip.android.data.remote.mapper.searchhistory

import com.arttrip.android.data.remote.model.searchhistory.SearchHistoryResponseDto
import com.arttrip.android.domain.model.recentsearch.RecentSearch

fun SearchHistoryResponseDto.toDomain(): List<RecentSearch> =
    items.map { dto ->
        RecentSearch(
            id = dto.searchHistoryId,
            content = dto.content,
        )
    }
