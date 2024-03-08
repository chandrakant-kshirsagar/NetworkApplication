package com.devrex.networkapplication.presentation.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.devrex.networkapplication.presentation.component.CircularProgressBar
import com.devrex.networkapplication.presentation.navigation.AxisDepositNavigator
import kotlinx.coroutines.Dispatchers

@Composable
fun MovieItem(
    movieId: String? = "",
    imageUrl: String? = "",
    title: String? = "This is Title",
    subTitle: String? = "subtitle",
    progress: Float? = 0.4F,
    navHostController: NavHostController,
) {
    val coroutineDispatcher by remember {
        mutableStateOf(Dispatchers.IO)
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navHostController.navigate(AxisDepositNavigator.movieDetailsRoute(movieId.orEmpty()))
            }

    ) {
        val (cardViewId, titleId, subTitleId, progressId) = createRefs()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(cardViewId) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

            Box(modifier = Modifier.height(200.dp)) {
                if (imageUrl.isNullOrEmpty().not()) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://image.tmdb.org/t/p/w440_and_h660_face/$imageUrl")
                                .allowConversionToBitmap(true)
                                .dispatcher(coroutineDispatcher)
                                .size(coil.size.Size.ORIGINAL) // Set the target size to load the image at.
                                .build()
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent, Color.Black
                                    ), startY = 300f
                                )
                            )
                    )
                }
            }

        }
        Box(
            modifier = Modifier
                .size(30.dp)
                .constrainAs(progressId) {
                    top.linkTo(cardViewId.bottom)
                    bottom.linkTo(cardViewId.bottom)
                    start.linkTo(cardViewId.start, 8.dp)
                },
            contentAlignment = Alignment.CenterStart
        ) {
            CircularProgressBar(
                percentage = progress ?: 0.4F,
                maxProgress = 100,
                showAnimation = false,
                fontSize = 12.sp
            )
        }
        if (title != null) {
            Text(
                modifier = Modifier
                    .constrainAs(titleId) {
                        top.linkTo(cardViewId.bottom, 24.dp)
                        start.linkTo(cardViewId.start, 8.dp)
                    },
                text = title, color = Color.Black, style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }
        if (subTitle != null) {
            Text(
                modifier = Modifier
                    .constrainAs(subTitleId) {
                        top.linkTo(titleId.bottom, 1.dp)
                        start.linkTo(titleId.start)
                    },
                text = subTitle, color = Color.Black, style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }
    }


}