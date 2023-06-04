package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.watch_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShow
import com.hosseinmohammadkarimi.tvshowskotlin.data.repositories.TVShowsRepository
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TVShowsWatchListViewModel @Inject constructor(
    private val repository: TVShowsRepository
): ViewModel() {

    private val _localTVShows = mutableStateOf<List<TVShow>>(emptyList())
    val localTVShows: State<List<TVShow>> = _localTVShows

    private val _uiEvent = MutableSharedFlow<UIEvents>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getLocalTVShows()
    }

    private fun getLocalTVShows() {
        viewModelScope.launch {
            repository.getLocalTVShows().collect {
                _localTVShows.value = it
            }
        }
    }

    suspend fun deleteTVShowFromLocal(tvShows: TVShow) {
        repository.deleteTVShowFromLocal(tvShows)
        _uiEvent.emit(
            UIEvents.ShowSnackbar(
                message = "از لیست علاقمندی ها حذف شد"
            )
        )
    }
}