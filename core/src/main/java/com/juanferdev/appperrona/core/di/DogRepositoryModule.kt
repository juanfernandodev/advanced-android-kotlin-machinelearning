package com.juanferdev.appperrona.core.di

import com.juanferdev.appperrona.core.doglist.DogRepository
import com.juanferdev.appperrona.core.doglist.DogRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
fun interface DogRepositoryModule {

    @Binds
    fun bindDogRepository(
        dogRepository: DogRepository
    ): DogRepositoryContract
}