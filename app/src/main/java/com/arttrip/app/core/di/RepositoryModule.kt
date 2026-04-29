package com.arttrip.app.core.di

import com.arttrip.app.data.repository.AuthRepositoryImpl
import com.arttrip.app.data.repository.BookmarkRepositoryImpl
import com.arttrip.app.data.repository.ExhibitRepositoryImpl
import com.arttrip.app.data.repository.HomeRepositoryImpl
import com.arttrip.app.data.repository.MapRepositoryImpl
import com.arttrip.app.data.repository.NoticeRepositoryImpl
import com.arttrip.app.data.repository.ProfileRepositoryImpl
import com.arttrip.app.data.repository.ReviewRepositoryImpl
import com.arttrip.app.data.repository.SearchHistoryRepositoryImpl
import com.arttrip.app.data.repository.UserTasteRepositoryImpl
import com.arttrip.app.domain.repository.AuthRepository
import com.arttrip.app.domain.repository.BookmarkRepository
import com.arttrip.app.domain.repository.ExhibitRepository
import com.arttrip.app.domain.repository.HomeRepository
import com.arttrip.app.domain.repository.MapRepository
import com.arttrip.app.domain.repository.NoticeRepository
import com.arttrip.app.domain.repository.ProfileRepository
import com.arttrip.app.domain.repository.ReviewRepository
import com.arttrip.app.domain.repository.SearchHistoryRepository
import com.arttrip.app.domain.repository.UserTasteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun authRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun keywordRepository(keywordRepositoryImpl: UserTasteRepositoryImpl): UserTasteRepository

    @Singleton
    @Binds
    abstract fun homeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Singleton
    @Binds
    abstract fun exhibitRepository(exhibitRepositoryImpl: ExhibitRepositoryImpl): ExhibitRepository

    @Singleton
    @Binds
    abstract fun bookmarkRepository(bookmarkRepository: BookmarkRepositoryImpl): BookmarkRepository

    @Singleton
    @Binds
    abstract fun reviewRepository(reviewRepositoryImpl: ReviewRepositoryImpl): ReviewRepository

    @Singleton
    @Binds
    abstract fun profileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Singleton
    @Binds
    abstract fun recentSearchRepository(searchHistoryRepositoryImpl: SearchHistoryRepositoryImpl): SearchHistoryRepository

    @Singleton
    @Binds
    abstract fun mapRepository(mapRepositoryImpl: MapRepositoryImpl): MapRepository

    @Singleton
    @Binds
    abstract fun noticeRepository(noticeRepositoryImpl: NoticeRepositoryImpl): NoticeRepository
}
