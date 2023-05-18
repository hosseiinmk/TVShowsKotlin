package com.hosseinmohammadkarimi.tvshowskotlin.di

import com.hosseinmohammadkarimi.tvshowskotlin.data.remote.TVShowsApi
import com.hosseinmohammadkarimi.tvshowskotlin.data.repositories.TVShowsRepository
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTVShowsApi(): TVShowsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TVShowsApi::class.java)

    @Singleton
    @Provides
    fun provideTVShowsRepository(
        api: TVShowsApi
    ) = TVShowsRepository(api)
}