package com.hosseinmohammadkarimi.tvshowskotlin.utilities

sealed class Resources<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T? = null): Resources<T>(data)
    class Error<T>(message: String? = null): Resources<T>(message = message)
}