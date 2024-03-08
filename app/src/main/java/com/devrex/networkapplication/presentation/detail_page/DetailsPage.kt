package com.devrex.networkapplication.presentation.detail_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.devrex.networkapplication.presentation.component.CircularProgressBar
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsPage(
    viewModel: DetailsPageViewModel? = null,
) {
    val coroutineDispatcher by remember {
        mutableStateOf(Dispatchers.IO)
    }

    val viewState by viewModel!!.viewState.collectAsState()
    val count by remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(Unit) {
        viewModel?.callDetailsApi()
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Title",
                textAlign = TextAlign.Center
            )
        }, colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
        ), navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Menu, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        })
    }

    ) {
        if (viewState.showProgress) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading...")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)

                ) {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://image.tmdb.org/t/p/w440_and_h660_face/${viewState.movieDetailsModel?.backDropImageUrl}")
                                .allowConversionToBitmap(true)
                                .dispatcher(coroutineDispatcher)
                                .size(Size.ORIGINAL) // Set the target size to load the image at.
                                .build()
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Card(
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {

                        Image(
                            modifier = Modifier.size(100.dp, 130.dp),
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https://image.tmdb.org/t/p/w440_and_h660_face/${viewState.movieDetailsModel?.posterImageUrl}")
                                    .allowConversionToBitmap(true)
                                    .dispatcher(coroutineDispatcher)
                                    .size(Size.ORIGINAL) // Set the target size to load the image at.
                                    .build()
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )

                    }
                }
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.onBackground)
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Text(
                            modifier = Modifier,
                            text = viewState.movieDetailsModel?.title.orEmpty(),
                            style = TextStyle(
                                color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            modifier = Modifier,
                            text = viewState.movieDetailsModel?.releaseDate.orEmpty(),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Light,
                            )
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1F),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                            ) {
                                CircularProgressBar(
                                    fontSize = 12.sp,
                                    strokeWidth = 6,
                                    backgroundColor = Color.Transparent,
                                    percentage = viewState.movieDetailsModel?.rating ?: 0F,

                                    )

                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "User Score",
                                style = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight(0.05f)
                                .width(1.dp)
                        )
                        Row(
                            modifier = Modifier
                                .weight(1F)
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                            Text(
                                text = "Play Trailer",
                                style = TextStyle(
                                    color = Color.White
                                )
                            )
                        }
                    }


                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = viewState.generaList.invoke(),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Overview",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = viewState.movieDetailsModel?.overView.orEmpty(),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                        ),
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    viewState.movieDetailsModel?.productionCompanyList?.forEach { _ ->
                        val company =
                            viewState.movieDetailsModel?.productionCompanyList?.get(count)?.name
                        val country =
                            viewState.movieDetailsModel?.productionCompanyList?.get(count)?.originCountry

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1F)
                            ) {
                                if (viewState.movieDetailsModel?.productionCompanyList?.get(0) != null) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = company.orEmpty(),
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                        ),
                                    )
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = country.orEmpty(),
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal,
                                        ),
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1F)
                            ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = company.orEmpty(),
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                        ),
                                    )
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = country.orEmpty(),
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal,
                                        ),
                                    )

                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

            }
        }
    }
}

@Preview(name = "DetailsPage", showBackground = true)
@Composable
fun DetailsPagePreview() {
    DetailsPage(null)
}