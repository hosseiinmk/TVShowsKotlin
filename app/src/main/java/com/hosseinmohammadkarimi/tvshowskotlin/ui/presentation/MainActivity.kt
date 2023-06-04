package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.tv_show_details.TVShowDetailsScreen
import com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.tv_shows.TVShowsScreen
import com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.watch_list.TVShowsWatchList
import com.hosseinmohammadkarimi.tvshowskotlin.ui.theme.TVShowsKotlinTheme
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.Constants.TV_SHOWS
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.Constants.TV_SHOW_DETAILS
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.Constants.WATCH_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TVShowsKotlinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = TV_SHOWS
                    ) {
                        composable(route = TV_SHOWS) {
                            TVShowsScreen(navController = navController)
                        }
                        composable(
                            route = "$TV_SHOW_DETAILS/{tvShowId}",
                            arguments = listOf(
                                navArgument(name = "tvShowId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            TVShowDetailsScreen(navController = navController)
                        }
                        composable(route = WATCH_LIST) {
                            TVShowsWatchList(navController = navController)
                        }
                    }
                }
            }
        }
    }
}