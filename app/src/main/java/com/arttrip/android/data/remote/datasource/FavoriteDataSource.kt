package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.FavoriteApi
import com.arttrip.android.domain.model.favorite.BookmarkSortType
import javax.inject.Inject

class
FavoriteDataSource@Inject
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
            regions: List<String>? = null,
            countries: List<String>? = null,
            cursor: Int? = null,
            size: Int,
        ) = api.getFavorites(
            sortType = sortType.name,
            regions = regions,
            countries = countries,
            cursor = cursor,
            size = size,
        )
    }
