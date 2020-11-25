package ui

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRowFor
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.composecookbook.ui.demoui.spotify.data.Album
import com.guru.composecookbook.ui.demoui.spotify.data.SpotifyDataProvider
import utils.VerticalGrid
import utils.horizontalGradientBackground

@Composable
fun SpotifySearchScreen(onAlbumSelected: (Album) -> Unit) {
    val scrollState = rememberScrollState(0f)
    val surfaceGradient = listOf(MaterialTheme.colors.secondary, MaterialTheme.colors.surface)

    ScrollableColumn(
        scrollState = scrollState,
        modifier = Modifier.fillMaxSize().horizontalGradientBackground(surfaceGradient)
    ) {
        Column(modifier = Modifier.horizontalGradientBackground(surfaceGradient)) {
            Spacer(modifier = Modifier.height(40.dp))
            SpotifySearchBar()
            SpotifyTitle("Your Top Genre")
            SpotifySearchLane(onAlbumSelected)
            SpotifyTitle("Browse All")
            SpotifySearchGrid(onAlbumSelected)
        }
        Spacer(modifier = Modifier.height(200.dp))
    }
}

@Composable
fun SpotifySearchLane(onAlbumSelected: (Album) -> Unit) {
    val items = remember { SpotifyDataProvider.albums.asReversed() }
    LazyRowFor(items = items, modifier = Modifier.padding(start = 8.dp)) {
        SpotifySearchGridItem(it, modifier = Modifier.width(400.dp)) {
            onAlbumSelected.invoke(it)
        }
    }
}

@Composable
fun SpotifySearchBar() {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(16.dp)
            .clip(CircleShape),
        backgroundColor = MaterialTheme.colors.onSurface
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Modifier.padding(start = 8.dp)
            Icon(Icons.Default.Search, tint = MaterialTheme.colors.surface)
            Text(
                text = "Artists, songs, or podcasts",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                style = typography.h6.copy(fontSize = 14.sp),
                color = MaterialTheme.colors.surface
            )
        }
    }
}

@Composable
fun SpotifySearchGrid(onAlbumSelected: (Album) -> Unit) {
    val items = remember { SpotifyDataProvider.albums + SpotifyDataProvider.albums }
    //This is not Lazy at the moment Soon we will have LazyLayout coming then will
    //Update it so we have better performance
    VerticalGrid(columns = 6, modifier = Modifier.padding(horizontal = 8.dp)) {
        items.forEach {
            SpotifySearchGridItem(it) {
                onAlbumSelected.invoke(it)
            }
        }
    }
}
