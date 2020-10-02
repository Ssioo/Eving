package com.whoissio.eving.utils

class SingleEvent<T: Any>(var data: T, var handled: Boolean = false) {
    fun get() : T? = when {
        !handled -> {
            handled = true
            data
        }
        else -> null
    }
}