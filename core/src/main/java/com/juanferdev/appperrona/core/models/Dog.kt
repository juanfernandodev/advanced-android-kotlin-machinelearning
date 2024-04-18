package com.juanferdev.appperrona.core.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Dog(
    val id: Long = 0L,
    val index: Int = 0,
    val name: String = String(),
    val type: String = String(),
    val heightFemale: String = String(),
    val heightMale: String = String(),
    val imageUrl: String = String(),
    val lifeExpectancy: String = String(),
    val temperament: String = String(),
    val weightFemale: String = String(),
    val weightMale: String = String(),
    var inCollection: Boolean = true
) : Parcelable, Comparable<Dog> {
    override fun compareTo(other: Dog) =
        if (this.index > other.index) 1 else -1

}
