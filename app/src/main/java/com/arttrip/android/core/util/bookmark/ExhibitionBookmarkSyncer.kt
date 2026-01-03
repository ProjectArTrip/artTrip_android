package com.arttrip.android.core.util.bookmark

interface ExhibitionBookmarkSyncer {
    suspend fun sync(
        exhibitId: Int,
        target: Boolean,
    ): Boolean
}
