package com.hosseinmohammadkarimi.tvshowskotlin.data.repositories

import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShow
import com.hosseinmohammadkarimi.tvshowskotlin.data.remote.TVShowsApi
import com.hosseinmohammadkarimi.tvshowskotlin.data.responses.TVShowsResponse
import com.hosseinmohammadkarimi.tvshowskotlin.data.room.TVShowsDao
import com.hosseinmohammadkarimi.tvshowskotlin.data.room.TVShowsDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TVShowsRepositoryImp @Inject constructor(
    private val api: TVShowsApi,
    private val dao: TVShowsDao
) {
    suspend fun getTVShows(page: Int): TVShowsResponse = api.getTVShows(page)

    suspend fun insertTVShowsIntoLocal(tvShows: List<TVShow>) {
        dao.insertTVShowsIntoLocal(tvShows)
    }

    fun getLocalTVShows(): Flow<List<TVShow>> = dao.getLocalTVShows()
}