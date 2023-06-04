package com.hosseinmohammadkarimi.tvshowskotlin.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShow

@Database(
    entities = [TVShow::class],
    version = 1
)
abstract class TVShowsDatabase: RoomDatabase()  {

    abstract fun tvShowsDao(): TVShowsDao
}