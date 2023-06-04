package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.tv_show_details

import android.widget.ScrollView
import android.widget.Scroller
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hosseinmohammadkarimi.tvshowskotlin.R
import com.hosseinmohammadkarimi.tvshowskotlin.ui.theme.DarkBlue
import com.hosseinmohammadkarimi.tvshowskotlin.ui.theme.GrayTransparent
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.UIEvents
import kotlinx.coroutines.flow.collectLatest

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun TVShowDetailsScreen(
    navController: NavController,
    viewModel: TVShowDetailsViewModel = hiltViewModel()
) {

    val tvShowDetails by remember { viewModel.tvShowDetails }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(DarkBlue)
        ) {
            val (pager, thumbnail, name, startDate, status, description, btnBack) = createRefs()
            HorizontalPager(
                pageCount = tvShowDetails.pictures.size,
                modifier = Modifier.constrainAs(pager) {
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                    height = Dimension.value(180.dp)
                }
            ) {
                AsyncImage(
                    model = tvShowDetails.pictures[it],
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            AsyncImage(
                model = tvShowDetails.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(220.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .constrainAs(thumbnail) {
                        top.linkTo(parent.top, 128.dp)
                        start.linkTo(parent.start, 8.dp)
                    },
                contentScale = ContentScale.Crop
            )
            Text(
                text = tvShowDetails.name,
                modifier = Modifier.constrainAs(name) {
                    start.linkTo(thumbnail.end, 8.dp)
                    top.linkTo(pager.bottom, 8.dp)
                },
                color = Color.White
            )
            Text(
                text = "started on: ${tvShowDetails.startDate}",
                modifier = Modifier.constrainAs(startDate) {
                    start.linkTo(thumbnail.end, 8.dp)
                    top.linkTo(name.bottom, 8.dp)
                },
                color = Color.White
            )
            Text(
                text = tvShowDetails.status,
                modifier = Modifier.constrainAs(status) {
                    start.linkTo(thumbnail.end, 8.dp)
                    top.linkTo(startDate.bottom, 8.dp)
                },
                color = Color.White
            )
            Text(
                text = tvShowDetails.description,
                modifier = Modifier
                    .verticalScroll(rememberScrollState(0))
                    .constrainAs(description) {
                        top.linkTo(thumbnail.bottom, 16.dp)
                        start.linkTo(parent.start, 8.dp)
                        end.linkTo(parent.end, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                        width = Dimension.fillToConstraints
                        height= Dimension.fillToConstraints
                    },
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(GrayTransparent)
                    .constrainAs(btnBack) {
                        top.linkTo(parent.top, 8.dp)
                        start.linkTo(parent.start, 8.dp)
                    },
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}