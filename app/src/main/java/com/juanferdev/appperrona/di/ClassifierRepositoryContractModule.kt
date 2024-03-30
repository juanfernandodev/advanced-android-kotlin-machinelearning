package com.juanferdev.appperrona.di

import com.juanferdev.appperrona.machinelearning.ClassifierRepository
import com.juanferdev.appperrona.machinelearning.ClassifierRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ClassifierRepositoryContractModule {

    @Binds
    fun provideClassifierRepositoryContract(classifierRepository: ClassifierRepository): ClassifierRepositoryContract
}