package com.devrex.networkapplication.presentation.movie

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devrex.networkapplication.presentation.component.TabItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieList(
    viewModel: MovieViewModel? = null,
    navHostController: NavHostController,
) {
    val tabList = arrayListOf(
        TabItem("Latest"),
        TabItem("Popular"),
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState(pageCount = {
        tabList.size
    })

    val viewState by viewModel!!.viewState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel?.callApis()
    }
    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(key1 = pagerState.currentPage, pagerState.isScrollInProgress) {
        if (pagerState.isScrollInProgress.not()) {
            selectedTabIndex = pagerState.currentPage
        }
    }
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
            modifier = Modifier.fillMaxSize()
        ) {
            TabRow(selectedTabIndex = selectedTabIndex,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        height = 5.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                tabList.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = index == selectedTabIndex,
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        onClick = {
                            selectedTabIndex = index
                        },
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = tabItem.title.orEmpty(),
                                style = TextStyle(
                                    color = Color.White
                                )
                            )

                        }
                    }
                }


            }
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (it) {
                    0 -> MovieLatest(viewState.latestMovieList,navHostController)
                    1 -> MoviePopular(viewState.popularMovieList,navHostController)
                }
            }
        }
    }
}
