package com.arttrip.android.core.di

import com.arttrip.android.data.repository.ExhibitRepositoryImpl
import com.arttrip.android.data.repository.UserRepositoryImpl
import com.arttrip.android.domain.repository.ExhibitRepository
import com.arttrip.android.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun userRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun exhibitRepository(exhibitRepositoryImpl: ExhibitRepositoryImpl): ExhibitRepository
}
