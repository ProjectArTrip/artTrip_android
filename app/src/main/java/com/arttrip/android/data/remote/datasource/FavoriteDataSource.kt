package com.arttrip.android.data.remote.datasource

import com.arttrip.android.data.remote.api.FavoriteApi
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
    }
