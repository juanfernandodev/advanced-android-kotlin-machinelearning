package com.juanferdev.appperrona.parcelables

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    val email: String,
    val authenticationToken: String
) : Parcelable
