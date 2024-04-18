package com.juanferdev.appperrona.auth.auth

sealed class AuthFieldStatus {
    class Email(val messageId: Int) : AuthFieldStatus()
    class Password(val messageId: Int) : AuthFieldStatus()
    class ConfirmPassword(val messageId: Int) :
        AuthFieldStatus()

    data object NoError : AuthFieldStatus()
}