package com.juanferdev.appperrona.di

import com.juanferdev.appperrona.machinelearning.Classifier
import com.juanferdev.appperrona.machinelearning.ClassifierContract
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