package com.hosseinmohammadkarimi.tvshowskotlin.di

import android.app.Application
import androidx.room.Room
import com.hosseinmohammadkarimi.tvshowskotlin.data.remote.TVShowsApi
import com.hosseinmohammadkarimi.tvshowskotlin.data.repositories.TVShowsRepository
import com.hosseinmohammadkarimi.tvshowskotlin.data.room.TVShowsDatabase
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

    @Provides
    @Singleton
    fun provideTVShowsApi(): TVShowsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TVShowsApi::class.java)

    @Provides
    @Singleton
    fun provideTVShowsDatabase(application: Application): TVShowsDatabase = Room.databaseBuilder(
        application,
        TVShowsDatabase::class.java,
        "tv_shows_database"
    ).build()

    @Provides
    @Singleton
    fun provideTVShowsRepository(
        api: TVShowsApi,
        db: TVShowsDatabase
    ): TVShowsRepository = TVShowsRepository(api, db.tvShowsDao())
}