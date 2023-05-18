package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.tvshows

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShow
import com.hosseinmohammadkarimi.tvshowskotlin.data.repositories.TVShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVShowsViewModel @Inject constructor(
    private val repository: TVShowsRepository
) : ViewModel() {

    private var currentPage = 1

    var tvShows = mutableStateOf<List<TVShow>>(listOf())

    init {
        viewModelScope.launch {
            tvShows.value = repository.getTVShows(currentPage).tvShows
            currentPage++
            Log.d("test", "test")
        }
    }
}