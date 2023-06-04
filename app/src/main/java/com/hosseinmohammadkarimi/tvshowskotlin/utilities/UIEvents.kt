package com.hosseinmohammadkarimi.tvshowskotlin.utilities

sealed class UIEvents {

    data class ShowSnackbar(
        val message: String,
        val actionLabel: String? = null
    ): UIEvents()
}