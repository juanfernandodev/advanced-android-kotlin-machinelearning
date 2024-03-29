package com.juanferdev.appperrona.di

import com.juanferdev.appperrona.doglist.DogRepository
import com.juanferdev.appperrona.doglist.DogRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DogRepositoryModule {

    @Binds
    abstract fun bindDogRepository(
        dogRepository: DogRepository
    ): DogRepositoryContract
}