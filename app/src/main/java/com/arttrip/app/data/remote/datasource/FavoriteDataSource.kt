package com.arttrip.app.data.remote.datasource

import com.arttrip.app.data.remote.api.FavoriteApi
import com.arttrip.app.domain.model.favorite.BookmarkSortType
import javax.inject.Inject

class FavoriteDataSource
    @Inject
    constructor(
        private val api: FavoriteApi,
    ) {
        suspend fun postFavorite(exhibitId: Int) =
            api.postFavorite(
                exhibitId = exhibitId,
            )

        suspend fun deleteFavorite(exhibitId: Int) =
            api.deleteFavorite(
                exhibitId = exhibitId,
            )

        suspend fun getFavorites(
            sortType: BookmarkSortType,
            region: String? = null,
            country: String? = null,
            cursor: Int? = null,
            size: Int,
        ) = api.getFavorites(
            sortType = sortType.name,
            region = region,
            country = country,
            cursor = cursor,
            size = size,
        )
    }
