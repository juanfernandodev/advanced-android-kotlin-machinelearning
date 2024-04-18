package com.juanferdev.appperrona.core.di

import com.juanferdev.appperrona.core.api.ApiService
import com.juanferdev.appperrona.core.api.constantsurls.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(com.juanferdev.appperrona.core.api.ApiServiceInterceptor)
            .build()

}