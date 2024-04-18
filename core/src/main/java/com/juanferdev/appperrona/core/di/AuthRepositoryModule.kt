package com.juanferdev.appperrona.core.di

import com.juanferdev.appperrona.core.auth.AuthRepository
import com.juanferdev.appperrona.core.auth.AuthRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
fun interface AuthRepositoryModule {

    @Binds
    fun provideAuthRepository(authRepository: AuthRepository): AuthRepositoryContract
}