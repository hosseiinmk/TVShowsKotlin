package com.hosseinmohammadkarimi.tvshowskotlin.data.responses

import com.google.gson.annotations.SerializedName
import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShowDetails

data class TVShowDetailsResponse(
    @SerializedName("tvShow")
    val tvShowDetails: TVShowDetails
)