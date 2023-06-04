package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.tvShows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hosseinmohammadkarimi.tvshowskotlin.R
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.Constants.WATCH_LIST
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.UIEvents
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TVShowsScreen(
    navController: NavController,
    viewModel: TVShowsViewModel = viewModel()
) {
    val tvShows by remember { viewModel.tvShows }
    val isLoading by remember { viewModel.isLoading }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarHostState) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UIEvents.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true,
                        actionLabel = event.actionLabel
                    ).let {
                        if (it == SnackbarResult.ActionPerformed) {
                            if (tvShows.isEmpty()) viewModel.reloadCurrentPage()
                            else viewModel.loadMore()
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(WATCH_LIST)
                },
                containerColor = Color.Green
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            }
        }
    ) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val (column, circleProgress) = createRefs()
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .constrainAs(column) {
                        width = Dimension.matchParent
                        height = Dimension.matchParent
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(tvShows.size) { position ->
                        TVShowItem(
                            tvShow = tvShows[position]
                        )
                    }
                    item {
                        if (!isLoading && tvShows.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    viewModel.loadMore()
                                },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_load_more),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(circleProgress) {
                        centerHorizontallyTo(parent)
                        centerVerticallyTo(parent)
                    },
                    color = Color.Green
                )
            }
        }
    }
}
