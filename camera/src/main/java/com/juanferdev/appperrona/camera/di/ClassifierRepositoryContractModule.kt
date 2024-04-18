package com.juanferdev.appperrona.camera.di

import com.juanferdev.appperrona.camera.machinelearning.ClassifierRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
fun interface ClassifierRepositoryContractModule {

    @Binds
    fun provideClassifierRepositoryContract(classifierRepository: ClassifierRepository): com.juanferdev.appperrona.camera.machinelearning.ClassifierRepositoryContract
}