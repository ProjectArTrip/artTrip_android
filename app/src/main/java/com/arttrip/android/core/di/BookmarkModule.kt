package com.arttrip.android.core.di

import com.arttrip.android.core.util.bookmark.ExhibitionBookmarkSyncer
import com.arttrip.android.core.util.bookmark.ExhibitionBookmarkSyncerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BookmarkModule {
    @Binds
    @Singleton
    abstract fun bindExhibitionBookmarkSyncer(impl: ExhibitionBookmarkSyncerImpl): ExhibitionBookmarkSyncer
}
