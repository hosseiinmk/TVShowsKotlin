package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.tvshows

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TVShowsListScreen(
    viewModel: TVShowsViewModel = viewModel()
) {
    val tvShows by remember {
        viewModel.tvShows
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(tvShows.size) {
            TVShowItem(tvShows[it])
        }
    }
}