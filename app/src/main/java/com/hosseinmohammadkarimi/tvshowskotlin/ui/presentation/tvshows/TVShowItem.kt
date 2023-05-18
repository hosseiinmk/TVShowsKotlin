package com.hosseinmohammadkarimi.tvshowskotlin.ui.presentation.tvshows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hosseinmohammadkarimi.tvshowskotlin.data.models.TVShow

@Composable
fun TVShowItem(
    tvShow: TVShow
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(bottom = 8.dp)
            .background(Color.LightGray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = tvShow.thumbnail,
            contentDescription = "thumbnail",
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                color = Color.Black,
                text = tvShow.name
            )
            Text(
                color = Color.Black,
                text = "started on: ${tvShow.startDate}"
            )
            Text(
                color = Color.Black,
                text = tvShow.status
            )
        }
    }
}


@Composable
@Preview
fun TVShowItemPreview() {
//    TVShowItem()
}