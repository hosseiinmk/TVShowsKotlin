package com.hosseinmohammadkarimi.tvshowskotlin.data.repositories

import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShow
import com.hosseinmohammadkarimi.tvshowskotlin.data.remote.TVShowsApi
import com.hosseinmohammadkarimi.tvshowskotlin.data.responses.TVShowDetailsResponse
import com.hosseinmohammadkarimi.tvshowskotlin.data.responses.TVShowsResponse
import com.hosseinmohammadkarimi.tvshowskotlin.data.room.TVShowsDao
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.Resources
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TVShowsRepository @Inject constructor(
    private val api: TVShowsApi,
    private val dao: TVShowsDao
) {
    suspend fun getTVShows(page: Int): Resources<TVShowsResponse> = try {
        Resources.Success(api.getTVShows(page))
    } catch (e: Exception) {
        Resources.Error(message = "داده دریافت نشد")
    }

    suspend fun getTVShowDetails(id: Int): Resources<TVShowDetailsResponse> = try {
        Resources.Success(api.getTVShowDetails(id))
    } catch (e: Exception) {
        Resources.Error(message = "داده دریافت نشد")
    }

    suspend fun insertTVShowIntoLocal(tvShows: TVShow) {
        dao.insertTVShowIntoLocal(tvShows)
    }

    suspend fun deleteTVShowFromLocal(tvShow: TVShow) {
        dao.deleteTVShowFromLocal(tvShow)
    }

    fun getLocalTVShows(): Flow<List<TVShow>> = dao.getLocalTVShows()
    fun getLocalTVShow(id: String): Flow<TVShow> = dao.getLocalTVShow(id)
}