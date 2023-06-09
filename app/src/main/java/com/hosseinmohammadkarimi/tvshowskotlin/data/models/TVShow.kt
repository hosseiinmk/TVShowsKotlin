package com.hosseinmohammadkarimi.tvshowskotlin.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tv_shows")
data class TVShow(
    @PrimaryKey
    val id: Int,
    val name: String,
    @SerializedName("start_date")
    val startDate: String,
    val status: String,
    @SerializedName("image_thumbnail_path")
    val thumbnail: String,
)
