package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.watch_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hosseinmohammadkarimi.tvshowskotlin.R
import com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.tv_shows.TVShowItem
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.UIEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TVShowsWatchList(
    navController: NavController,
    viewModel: TVShowsWatchListViewModel = hiltViewModel()
) {

    val localTVShows = remember { viewModel.localTVShows }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(snackbarHostState) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UIEvents.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true,
                        actionLabel = event.actionLabel
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "علاقمندی ها", color = Color.White)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    colorResource(id = R.color.light_blue),
                    Color.Green
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn(content = {
                items(localTVShows.value) { tvShow ->
                    TVShowItem(
                        navController = navController,
                        tvShow = tvShow,
                        deleteButtonVisible = true,
                        markButtonVisible = false,
                        onDeleteButtonClick = {
                            coroutineScope.launch {
                                viewModel.deleteTVShowFromLocal(tvShow)
                            }
                        }
                    )
                }
                item { 
                    Spacer(modifier = Modifier.height(16.dp))
                }
            })
        }
    }
}