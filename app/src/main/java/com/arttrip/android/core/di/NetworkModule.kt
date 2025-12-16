package com.arttrip.android.core.di

import com.arttrip.android.data.remote.api.AuthApi
import com.arttrip.android.data.remote.api.ExhibitApi
import com.arttrip.android.data.remote.api.HomeApi
import com.arttrip.android.data.remote.interceptor.AuthInterceptor
import com.arttrip.android.data.remote.interceptor.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val BASE_URL = "https://dev.coffit.today"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    // 메인 OkHttpClient: AuthInterceptor + TokenAuthenticator 포함
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // 리프레시 전용 AuthApi (TokenAuthenticator에서만 사용)
    @Provides
    @Singleton
    @RefreshAuthApi
    fun provideRefreshAuthApi(loggingInterceptor: HttpLoggingInterceptor): AuthApi {
        val client =
            OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        val retrofit =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun provideExhibitApi(retrofit: Retrofit): ExhibitApi = retrofit.create(ExhibitApi::class.java)
}
