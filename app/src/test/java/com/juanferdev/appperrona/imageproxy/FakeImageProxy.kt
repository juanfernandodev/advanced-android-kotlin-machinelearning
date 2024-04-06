package com.juanferdev.appperrona.imageproxy

import android.graphics.Rect
import android.util.Pair
import androidx.camera.core.ImageInfo
import androidx.camera.core.ImageProxy
import androidx.camera.core.impl.TagBundle
import androidx.camera.core.impl.utils.ExifData

class FakeImageProxy : ImageProxy {
    override fun close() {
        //ImageProxy Closed!
    }

    override fun getCropRect() = Rect(0, 0, 0, 0)

    override fun setCropRect(rect: Rect?) {
        //It's not necessary
    }

    override fun getFormat() = 0

    override fun getHeight() = 0

    override fun getWidth() = 0

    override fun getPlanes() = emptyArray<ImageProxy.PlaneProxy>()

    override fun getImageInfo(): ImageInfo {
        class FakeImageInfo : ImageInfo {
            override fun getTagBundle() = TagBundle.create(Pair(String(), String()))

            override fun getTimestamp() = 1L

            override fun getRotationDegrees() = 0

            override fun populateExifData(exifBuilder: ExifData.Builder) {
                //It's not necessary
            }

        }

        return FakeImageInfo()
    }

    override fun getImage() = null
}

