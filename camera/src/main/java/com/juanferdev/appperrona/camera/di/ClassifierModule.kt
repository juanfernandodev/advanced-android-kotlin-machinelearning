package com.juanferdev.appperrona.camera.di

import android.content.Context
import com.juanferdev.appperrona.camera.machinelearning.LABEL_PATH
import com.juanferdev.appperrona.camera.machinelearning.MODEL_PATH
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.nio.MappedByteBuffer
import org.tensorflow.lite.support.common.FileUtil

@Module
@InstallIn(SingletonComponent::class)
object ClassifierModule {

    @Provides
    fun providerClassifierModel(@ApplicationContext context: Context): MappedByteBuffer =
        FileUtil.loadMappedFile(
            context,
            MODEL_PATH
        )


    @Provides
    fun providerClassifierLabel(@ApplicationContext context: Context): List<String> =
        FileUtil.loadLabels(
            context,
            LABEL_PATH
        )

}