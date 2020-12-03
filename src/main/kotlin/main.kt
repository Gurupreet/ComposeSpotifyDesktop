import androidx.compose.animation.Crossfade
import androidx.compose.animation.animate
import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.composecookbook.ui.demoui.spotify.data.Album
import com.guru.composecookbook.ui.demoui.spotify.data.SpotifyDataProvider
import ui.SpotifyHome
import ui.SpotifyLibrary
import ui.SpotifyNavType
import ui.SpotifySearchScreen
import ui.detail.SpotifyDetailScreen


fun main() = Window(title = "Compose Spotify Desktop") {
    val darkTheme = savedInstanceState { true }
    MaterialTheme(colors = if (darkTheme.value) DarkGreenColorPalette else LightGreenColorPalette) {
        SpotifyApp(darkTheme)
    }
}

@Composable
fun SpotifyApp(darkTheme: MutableState<Boolean>) {
    val spotifyNavItemState = savedInstanceState { SpotifyNavType.HOME }
    val showAlbumDetailState = savedInstanceState<Album?> { null }
    Box {
        Row {
            SpotifySideBar(spotifyNavItemState, showAlbumDetailState, darkTheme)
            SpotifyBodyContent(spotifyNavItemState.value, showAlbumDetailState)
        }
        PlayerBottomBar(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun SpotifyBodyContent(spotifyNavType: SpotifyNavType, album: MutableState<Album?>) {
    if (album.value != null) {
        SpotifyDetailScreen(album.value!!) {
            album.value = null
        }
    } else {
        Crossfade(current = spotifyNavType) { spotifyNavType ->
            when (spotifyNavType) {
                SpotifyNavType.HOME -> SpotifyHome { onAlbumSelected ->
                    album.value = onAlbumSelected
                }
                SpotifyNavType.SEARCH -> SpotifySearchScreen { onAlbumSelected ->
                    album.value = onAlbumSelected
                }
                SpotifyNavType.LIBRARY -> SpotifyLibrary()
            }
        }
    }
}

@Composable
fun SpotifySideBar(
    spotifyNavItemState: MutableState<SpotifyNavType>,
    showAlbumDetailState: MutableState<Album?>,
    darkTheme: MutableState<Boolean>
) {
    val selectedIndex = remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier.fillMaxHeight().preferredWidth(250.dp).background(MaterialTheme.colors.surface)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            imageFromResource(if (darkTheme.value) "spotify.png" else "spotifydark.png"),
            modifier = Modifier.padding(end = 16.dp, top = 16.dp, bottom = 16.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().clickable { darkTheme.value = !darkTheme.value }.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Toggle Theme", style = typography.h6.copy(fontSize = 14.sp), color = MaterialTheme.colors.onSurface)
            if (darkTheme.value) {
                Icon(imageVector = Icons.Default.Star, tint = Color.Yellow)
            } else {
                Icon(imageVector = Icons.Default.Star, tint = MaterialTheme.colors.onSurface)
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        SideBarNavItem("Home", Icons.Default.Home, spotifyNavItemState.value == SpotifyNavType.HOME) {
            spotifyNavItemState.value = SpotifyNavType.HOME
            showAlbumDetailState.value = null
            selectedIndex.value = -1
        }
        SideBarNavItem("Search", Icons.Default.Search, spotifyNavItemState.value == SpotifyNavType.SEARCH) {
            spotifyNavItemState.value = SpotifyNavType.SEARCH
            showAlbumDetailState.value = null
            selectedIndex.value = -1
        }
        SideBarNavItem("Your Library", Icons.Default.List, spotifyNavItemState.value == SpotifyNavType.LIBRARY) {
            spotifyNavItemState.value = SpotifyNavType.LIBRARY
            showAlbumDetailState.value = null
            selectedIndex.value = -1
        }
        Spacer(modifier = Modifier.height(20.dp))
        PlayListsSideBar(selectedIndex.value) {
            showAlbumDetailState.value = SpotifyDataProvider.albums[it]
            selectedIndex.value = it
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun PlayerBottomBar(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.secondary)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageFromResource("adele.jpeg"),
            modifier = Modifier.preferredSize(75.dp).padding(8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Someone Like you by Adele",
            style = typography.h6.copy(fontSize = 14.sp),
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(16.dp),
        )
        Icon(
            imageVector = Icons.Default.AddCircle,
            modifier = Modifier.padding(8.dp),
            tint = MaterialTheme.colors.onSurface
        )
        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
            Icon(
                imageVector = Icons.Default.Refresh,
                modifier = Modifier.padding(8.dp),
                tint = MaterialTheme.colors.onSurface
            )
            Icon(
                imageVector = Icons.Default.PlayArrow,
                modifier = Modifier.padding(8.dp),
                tint = MaterialTheme.colors.onSurface
            )
            Icon(imageVector = Icons.Default.Favorite, modifier = Modifier.padding(8.dp), tint = spotifyGreen)
        }

        Icon(imageVector = Icons.Default.List, modifier = Modifier.padding(8.dp), tint = MaterialTheme.colors.onSurface)
        Icon(
            imageVector = Icons.Default.Share,
            modifier = Modifier.padding(8.dp),
            tint = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun PlayListsSideBar(selectedIndex: Int, onPlayListSelected: (Int) -> Unit) {
    Text(
        "PLAYLISTS",
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
        color = MaterialTheme.colors.onSurface
    )
    LazyColumnForIndexed(items = SpotifyDataProvider.playLits) { index, playlist ->
        Text(
            playlist,
            modifier = Modifier.padding(8.dp).clickable { onPlayListSelected.invoke(index) },
            color = animate(
                if (index == selectedIndex) MaterialTheme.colors.onSurface else MaterialTheme.colors.onSecondary.copy(
                    alpha = 0.7f
                )
            ),
            style = if (index == selectedIndex) typography.h6 else typography.body1
        )
    }
}


@Composable
fun SideBarNavItem(title: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    val animatedBackgroundColor =
        animate(if (selected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface)
    val animatedContentColor =
        animate(if (selected) MaterialTheme.colors.onSurface else MaterialTheme.colors.onSecondary)
    Row(
        modifier = Modifier
            .fillMaxWidth().background(animatedBackgroundColor).clip(RoundedCornerShape(4.dp)).padding(16.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Icon(imageVector = icon, tint = animatedContentColor)
        Text(
            title,
            style = typography.body1,
            color = animatedContentColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}
