package com.juanferdev.appperrona.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    val email: String,
    val authenticationToken: String
) : Parcelable
