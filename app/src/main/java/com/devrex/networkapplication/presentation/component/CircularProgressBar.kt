package com.devrex.networkapplication.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    percentage: Float = 0.4F,
    maxProgress: Int = 100,
    radius: Dp = 50.dp,
    fontSize: TextUnit = 18.sp,
    strokeWidth: Int = 12,
    strokeColor: Color = Color.Green,
    showAnimation: Boolean = false,
    animDuration: Int = 1000,
    animDelay: Int = 0,
    backgroundColor:Color = MaterialTheme.colorScheme.onPrimaryContainer
) {

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        label = "",
        animationSpec = tween(
            durationMillis = animDuration, delayMillis = animDelay
        )
    )

    LaunchedEffect(Unit) {
        animationPlayed = showAnimation
    }

    Box(
        modifier = modifier
            .size(radius * 2F)
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = modifier
                .size(radius * 1.8F)

        ) {
            drawArc(
                color = strokeColor,
                -90F,
                if (showAnimation) (360 * currentPercentage.value) else (360 * percentage),
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toFloat(),
                    cap = StrokeCap.Round
                )
            )
        }
        Text(
            text = if (showAnimation) (currentPercentage.value * maxProgress).toInt()
                .toString() else (percentage * maxProgress).toInt().toString(),
            color = Color.White,
            fontSize = fontSize
        )
    }

}


@Preview(name = "CircularProgressBar", showBackground = true)
@Composable
fun CircularProgressBarPreview() {
    CircularProgressBar()
}

