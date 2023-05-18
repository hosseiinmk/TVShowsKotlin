package com.hosseinmohammadkarimi.tvshowskotlin.data.models

import com.google.gson.annotations.SerializedName

data class TVShow(
    val id: Int,
    val name: String,
    @SerializedName("start_date")
    val startDate: String,
    val status: String,
    @SerializedName("image_thumbnail_path")
    val thumbnail: String
)
