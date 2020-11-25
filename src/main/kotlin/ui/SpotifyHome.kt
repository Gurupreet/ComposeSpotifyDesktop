package ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.guru.composecookbook.ui.demoui.spotify.data.Album
import com.guru.composecookbook.ui.demoui.spotify.data.SpotifyDataProvider
import utils.horizontalGradientBackground

@Composable
fun SpotifyHome(onAlbumSelected: (Album) -> Unit) {
    val scrollState = rememberScrollState(0f)
    val surfaceGradient = listOf(MaterialTheme.colors.secondary, MaterialTheme.colors.surface)

    Surface {
        SpotifyHomeContent(scrollState = scrollState, surfaceGradient = surfaceGradient, onAlbumSelected)
    }
}

@Composable
fun SpotifyTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = MaterialTheme.colors.onSurface,
        style = typography.h5.copy(fontWeight = FontWeight.ExtraBold),
        modifier = modifier.padding(start = 24.dp, end = 4.dp, bottom = 8.dp, top = 24.dp)
    )
}


@Composable
fun SpotifyHomeContent(scrollState: ScrollState, surfaceGradient: List<Color>, onAlbumSelected: (Album) -> Unit) {
    ScrollableColumn(
        scrollState = scrollState,
        modifier = Modifier.horizontalGradientBackground(surfaceGradient).padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        SpotifyTitle("Good Evening")
        HomeLanesSection(onAlbumSelected)
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun HomeLanesSection(onAlbumSelected: (Album) -> Unit) {
    val categories = remember { SpotifyDataProvider.listOfSpotifyHomeLanes }
    categories.forEachIndexed { index, lane ->
        SpotifyTitle(text = lane)
        SpotifyLane(index, onAlbumSelected)
    }
}

@Composable
fun SpotifyLane(index: Int, onAlbumSelected: (Album) -> Unit) {
    val itemsEven = remember { SpotifyDataProvider.albums }
    val itemsOdd = remember { SpotifyDataProvider.albums.asReversed() }
    LazyRowFor(if (index % 2 == 0) itemsEven else itemsOdd) {
        SpotifyLaneItem(album = it) {
            onAlbumSelected.invoke(it)
        }
    }
}





































