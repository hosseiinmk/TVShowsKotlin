package com.hosseinmohammadkarimi.tvshowskotlin.data.models

import com.google.gson.annotations.SerializedName

data class TVShowDetails(
    val id: Int,
    val name: String,
    @SerializedName("start_date")
    val startDate: String,
    val status: String,
    @SerializedName("image_thumbnail_path")
    val thumbnail: String,
    val description: String,
    val pictures: List<String>
) {
    companion object {
        val emptyDetails = TVShowDetails(
            -1,
            "",
            "",
            "",
            "",
            "",
            emptyList())
    }
}
