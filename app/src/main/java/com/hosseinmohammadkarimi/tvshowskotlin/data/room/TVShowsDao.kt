package com.hosseinmohammadkarimi.tvshowskotlin.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShow
import kotlinx.coroutines.flow.Flow

@Dao
interface TVShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShowIntoLocal(tvShows: TVShow)

    @Delete
    suspend fun deleteTVShowFromLocal(tvShow: TVShow)

    @Query("SELECT * FROM tv_shows")
    fun getLocalTVShows(): Flow<List<TVShow>>

    @Query("SELECT * FROM tv_shows WHERE id = :id")
    fun getLocalTVShow(id: String): Flow<TVShow>
}