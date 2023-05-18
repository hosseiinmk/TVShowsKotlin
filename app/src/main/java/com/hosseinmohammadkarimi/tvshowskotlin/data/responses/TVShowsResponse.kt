package com.hosseinmohammadkarimi.tvshowskotlin.data.responses

import com.google.gson.annotations.SerializedName
import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShow

data class TVShowsResponse(
    val page: Int,
    val pages: Int,
    @SerializedName("tv_shows")
    val tvShows: List<TVShow>
)