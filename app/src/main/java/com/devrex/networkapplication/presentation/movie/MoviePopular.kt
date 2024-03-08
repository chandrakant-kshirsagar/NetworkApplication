package com.devrex.networkapplication.presentation.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devrex.networkapplication.domain.model.MovieResultModel

@Composable
fun MoviePopular(
    popularMovieList: ArrayList<MovieResultModel>?,
    navHostController: NavHostController
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp)
    ) {
        if (popularMovieList.isNullOrEmpty()) return@LazyVerticalGrid
        itemsIndexed(popularMovieList) { _, item ->

            Box(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                MovieItem(
                    movieId = item.id.toString(),
                    title = item.title,
                    subTitle = item.releaseDate,
                    imageUrl = item.posterPath,
                    progress = item.voteAverage,
                    navHostController = navHostController
                )
            }

        }
    }
}
