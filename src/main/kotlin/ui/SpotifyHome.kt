package ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.guru.composecookbook.ui.demoui.spotify.data.SpotifyDataProvider
import utils.horizontalGradientBackground

@Composable
fun SpotifyHome() {
    val scrollState = rememberScrollState(0f)
    val surfaceGradient = SpotifyDataProvider.spotifySurfaceGradient(true)

    Surface {
        SpotifyHomeContent(scrollState = scrollState, surfaceGradient = surfaceGradient)
    }
}

@Composable
fun SpotifyTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = Color.White,
        style = typography.h5.copy(fontWeight = FontWeight.ExtraBold),
        modifier = modifier.padding(start = 24.dp, end = 4.dp, bottom = 8.dp, top = 24.dp)
    )
}


@Composable
fun SpotifyHomeContent(scrollState: ScrollState, surfaceGradient: List<Color>) {
    ScrollableColumn(
        scrollState = scrollState,
        modifier = Modifier.horizontalGradientBackground(surfaceGradient).padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        SpotifyTitle("Good Evening")
        HomeLanesSection()
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun HomeLanesSection() {
    val categories = remember { SpotifyDataProvider.listOfSpotifyHomeLanes }
    categories.forEachIndexed { index, lane ->
        SpotifyTitle(text = lane)
        SpotifyLane(index)
    }
}

@Composable
fun SpotifyLane(index: Int) {
    val itemsEven = remember { SpotifyDataProvider.albums }
    val itemsOdd = remember { SpotifyDataProvider.albums.asReversed() }
    LazyRowFor(if (index % 2 == 0) itemsEven else itemsOdd) {
        SpotifyLaneItem(album = it)
    }
}





































