package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.tv_shows

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

    private val currentPage = mutableStateOf(1)
    private val lastPage = currentPage
    private val localTVShows = mutableStateOf<List<TVShow>>(emptyList())

    private val _uiEvent = MutableSharedFlow<UIEvents>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            getLocalTVShows()
            getTVShows()
        }
    }

    private fun getLocalTVShows() {
        viewModelScope.launch {
            repository.getLocalTVShows().collect {
                localTVShows.value = it
            }
        }
    }

    private fun getTVShows() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = repository.getTVShows(currentPage.value)) {
                is Resources.Success -> {
                    _tvShows.value += result.data?.tvShows!!
                    _isLoading.value = false
                }

                is Resources.Error -> {
                    _isLoading.value = false
                    currentPage.value = lastPage.value
                    _uiEvent.emit(UIEvents.ShowSnackbar(
                        message = result.message!!,
                        actionLabel = "تلاش مجدد"
                    ))
                }
            }
        }
    }

    fun reloadCurrentPage() {
        getTVShows()
    }

    fun loadMore() {
        currentPage.value++
        lastPage.value = currentPage.value
        getTVShows()
    }

    suspend fun insertTVShowIntoLocal(tvShows: TVShow) {
        repository.insertTVShowIntoLocal(tvShows)
        _uiEvent.emit(
            UIEvents.ShowSnackbar(
                message = "به لیست علاقمندی ها اضافه شد"
            )
        )
    }

    suspend fun deleteTVShowFromLocal(tvShows: TVShow) {
        repository.deleteTVShowFromLocal(tvShows)
        _uiEvent.emit(
            UIEvents.ShowSnackbar(
                message = "از لیست علاقمندی ها حذف شد"
            )
        )
    }

    fun isTVShowMarked(tvShow: TVShow): Boolean {
        localTVShows.value.forEach(
            action = {
                if (it == tvShow) return true
            }
        )
        return false
    }
}