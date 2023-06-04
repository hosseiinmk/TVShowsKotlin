package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.composes.tv_shows

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hosseinmohammadkarimi.tvshowskotlin.R
import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShow
import com.hosseinmohammadkarimi.tvshowskotlin.utilities.Constants.TV_SHOW_DETAILS

@Composable
fun TVShowItem(
    navController: NavController,
    tvShow: TVShow,
    deleteButtonVisible: Boolean = false,
    markButtonVisible: Boolean = true,
    isMarked: Boolean = false,
    onMarkButtonClick: (Boolean) -> Unit = {},
    onDeleteButtonClick: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable(
                onClick = {
                    navController.navigate("$TV_SHOW_DETAILS/${tvShow.id}")
                }
            )
    ) {
        val (box, thumbnail, name, startDate, status, btnMark, btnDelete) = createRefs()
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(id = R.color.light_blue))
                .constrainAs(box) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.value(110.dp)
                }
        )
        AsyncImage(
            model = tvShow.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(thumbnail) {
                    start.linkTo(parent.start, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                },
            contentScale = ContentScale.Crop
        )
        Text(
            text = tvShow.name,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(thumbnail.end, 8.dp)
                top.linkTo(box.top, 8.dp)
            },
            color = Color.White
        )
        Text(
            text = "started on: ${tvShow.startDate}",
            modifier = Modifier.constrainAs(startDate) {
                start.linkTo(thumbnail.end, 8.dp)
                top.linkTo(name.bottom, 8.dp)
            },
            color = Color.White
        )
        Text(
            text = tvShow.status,
            modifier = Modifier.constrainAs(status) {
                start.linkTo(thumbnail.end, 8.dp)
                top.linkTo(startDate.bottom, 8.dp)
            },
            color = Color.White
        )
        if (markButtonVisible) {
            IconButton(
                onClick = {
                    onMarkButtonClick(isMarked)
                },
                modifier = Modifier
                    .constrainAs(btnMark) {
                        top.linkTo(box.top, 8.dp)
                        end.linkTo(parent.end, 16.dp)
                    }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isMarked) R.drawable.ic_filled_mark
                        else R.drawable.ic_empty_mark
                    ),
                    contentDescription = null
                )
            }
        }
        if (deleteButtonVisible) {
            IconButton(
                onClick = {
                    onDeleteButtonClick()
                },
                modifier = Modifier
                    .constrainAs(btnDelete) {
                        end.linkTo(parent.end, 16.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null
                )
            }
        }
    }
}