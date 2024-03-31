package com.juanferdev.appperrona.utils

import androidx.core.util.PatternsCompat

fun String.isValidEmail(): Boolean {
    return this.isNotBlank()
            && PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()
}