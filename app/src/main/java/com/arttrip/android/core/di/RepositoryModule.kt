package com.arttrip.android.core.di

import com.arttrip.android.data.repository.AuthRepositoryImpl
import com.arttrip.android.data.repository.BookmarkRepositoryImpl
import com.arttrip.android.data.repository.ExhibitRepositoryImpl
import com.arttrip.android.data.repository.HomeRepositoryImpl
import com.arttrip.android.data.repository.KeywordRepositoryImpl
import com.arttrip.android.data.repository.ReviewRepositoryImpl
import com.arttrip.android.domain.repository.AuthRepository
import com.arttrip.android.domain.repository.BookmarkRepository
import com.arttrip.android.domain.repository.ExhibitRepository
import com.arttrip.android.domain.repository.HomeRepository
import com.arttrip.android.domain.repository.KeywordRepository
import com.arttrip.android.domain.repository.ReviewRepository
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
    abstract fun keywordRepository(keywordRepositoryImpl: KeywordRepositoryImpl): KeywordRepository

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
}
