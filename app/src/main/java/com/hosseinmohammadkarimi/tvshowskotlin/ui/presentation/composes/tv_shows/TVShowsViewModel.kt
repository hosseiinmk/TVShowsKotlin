package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.tvShows

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShow
import com.hosseinmohammadkarimi.tvshowskotlin.data.repositories.TVShowsRepository
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.Resources
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVShowsViewModel @Inject constructor(
    private val repository: TVShowsRepository
) : ViewModel() {

    private val _tvShows = mutableStateOf<List<TVShow>>(emptyList())
    val tvShows: State<List<TVShow>> = _tvShows

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val error = mutableStateOf("")
    private val currentPage = mutableStateOf(1)
    private var lastPage = currentPage.value

    private val _uiEvent = MutableSharedFlow<UIEvents>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getTVShows()
    }

    private fun getTVShows() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getTVShows(currentPage.value)) {
                is Resources.Success -> {
                    _tvShows.value += result.data?.tvShows!!
                    error.value = ""
                    _isLoading.value = false
                }

                is Resources.Error -> {
                    error.value = result.message!!
                    _isLoading.value = false
                    currentPage.value = lastPage
                    _uiEvent.emit(UIEvents.ShowSnackbar(message = error.value))
                }
            }
        }
    }

    fun reloadCurrentPage() {
        getTVShows()
    }

    fun loadMore() {
        currentPage.value++
        lastPage = currentPage.value
        getTVShows()
    }

    fun onEvent(event: TVShowsEvent) {
        when (event) {
            is TVShowsEvent.OnTVShowItemClick -> {
            }
        }
    }
}