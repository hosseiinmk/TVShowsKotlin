package com.hosseinmohammadkarimi.tvshowskotlin.data.remote

import com.hosseinmohammadkarimi.tvshowskotlin.data.responses.TVShowsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TVShowsApi {

    @GET("most-popular")
    suspend fun getTVShows(
        @Query("page") page: Int
    ) : TVShowsResponse
}