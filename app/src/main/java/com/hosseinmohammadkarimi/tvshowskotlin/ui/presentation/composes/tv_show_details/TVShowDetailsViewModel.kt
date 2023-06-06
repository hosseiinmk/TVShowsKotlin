package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.tv_show_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShowDetails
import com.hosseinmohammadkarimi.tvshowskotlin.data.repositories.TVShowsRepository
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.Resources
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVShowDetailsViewModel @Inject constructor(
    private val repository: TVShowsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _tvShowDetails = mutableStateOf(TVShowDetails.emptyDetails)
    val tvShowDetails: State<TVShowDetails> = _tvShowDetails

    private val _uiEvent = MutableSharedFlow<UIEvents>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var tvShowId = -1

    init {
        savedStateHandle.get<Int>("tvShowId")?.let {
            Log.d("tvShowDetails", "tvShowId: $it")
            tvShowId = it
            getTVShowDetails(it)
        }
    }

    private fun getTVShowDetails(id: Int) {
        viewModelScope.launch {
            when(val result = repository.getTVShowDetails(id)) {
                is Resources.Success -> {
                    _tvShowDetails.value = result.data?.tvShowDetails!!
                }
                is Resources.Error -> {
                    _uiEvent.emit(
                        UIEvents.ShowSnackbar(
                            message = result.message!!,
                            actionLabel = "تلاش مجدد"
                        )
                    )
                }
            }
        }
    }

    fun reloadTVShowDetails() {
        getTVShowDetails(tvShowId)
    }
}