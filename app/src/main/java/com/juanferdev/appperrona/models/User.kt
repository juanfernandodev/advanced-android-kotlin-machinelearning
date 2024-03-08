package com.juanferdev.appperrona.models

import android.app.Activity
import android.content.Context


class User(
    val id: Long,
    val email: String,
    val authenticationToken: String
) {

    companion object {
        private const val AUTH_PREFS = "auth_prefs"
        private const val ID_KEY = "id"
        private const val EMAIL_KEY = "email"
        private const val AUTH_TOKEN_KEY = "auth_token"

        fun setLoggedInUser(activity: Activity, user: User) {
            activity.getSharedPreferences(
                AUTH_PREFS,
                Context.MODE_PRIVATE
            ).also {
                it.edit()
                    .putLong(ID_KEY, user.id)
                    .putString(EMAIL_KEY, user.email)
                    .putString(AUTH_TOKEN_KEY, user.authenticationToken)
                    .apply()
            }
        }

        fun getLoggedInUser(activity: Activity): User? {
            with(
                activity.getSharedPreferences(
                    AUTH_PREFS, Context.MODE_PRIVATE
                )
            ) {
                val idKey = this.getLong(ID_KEY, 0L)
                val email = this.getString(EMAIL_KEY, String()) ?: String()
                val token = this.getString(AUTH_TOKEN_KEY, String()) ?: String()
                var user: User? = null
                if (idKey != 0L && email.isNotEmpty() && token.isNotEmpty()) {
                    user = User(idKey, email, token)
                }
                return user
            }
        }

        fun deleteLoggedInUser(activity: Activity) {
            with(
                activity.getSharedPreferences(
                    AUTH_PREFS, Context.MODE_PRIVATE
                )
            ) {
                this.edit().clear().apply()
            }
        }
    }


}
