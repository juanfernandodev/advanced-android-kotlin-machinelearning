package com.juanferdev.appperrona.camera.di

import com.juanferdev.appperrona.camera.machinelearning.Classifier
import com.juanferdev.appperrona.camera.machinelearning.ClassifierContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
fun interface ClassifierContractModule {
    @Binds
    fun providerClassifierContract(classifier: Classifier): ClassifierContract
}