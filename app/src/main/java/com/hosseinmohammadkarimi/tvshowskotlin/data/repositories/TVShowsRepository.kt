package com.hosseinmohammadkarimi.tvshowskotlin.data.repositories

import com.hosseinmohammadkarimi.tvshowskotlin.data.remote.TVShowsApi
import com.hosseinmohammadkarimi.tvshowskotlin.data.responses.TVShowsResponse
import javax.inject.Inject

class TVShowsRepository @Inject constructor(
    private val api: TVShowsApi
) {
    suspend fun getTVShows(page: Int) : TVShowsResponse = api.getTVShows(page)
}