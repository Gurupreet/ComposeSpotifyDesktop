import androidx.compose.animation.Crossfade
import androidx.compose.animation.animate
import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
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
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.composecookbook.ui.demoui.spotify.data.Album
import com.guru.composecookbook.ui.demoui.spotify.data.SpotifyDataProvider
import ui.SpotifyHome
import ui.SpotifyNavType
import ui.SpotifySearchScreen
import ui.detail.SpotifyDetailScreen

fun main() = Window {
    MaterialTheme {
        SpotifyApp()
    }
}

@Composable
fun SpotifyApp() {
    val spotifyNavItemState = savedInstanceState { SpotifyNavType.HOME }
    val showAlbumDetailState = savedInstanceState<Album?> { null }

    Box {
        Row {
            SpotifySideBar(spotifyNavItemState, showAlbumDetailState)
            SpotifyBodyContent(spotifyNavItemState.value, showAlbumDetailState.value)
        }
        PlayerBottomBar(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun SpotifyBodyContent(spotifyNavType: SpotifyNavType, album: Album?) {
    if (album != null) {
           SpotifyDetailScreen(album)
    } else {
        Crossfade(current = spotifyNavType) { spotifyNavType ->
            when (spotifyNavType) {
                SpotifyNavType.HOME -> SpotifyHome()
                SpotifyNavType.SEARCH -> SpotifySearchScreen()
                SpotifyNavType.LIBRARY -> Text("Coming soon..")
            }
        }
    }
}

@Composable
fun SpotifySideBar(spotifyNavItemState: MutableState<SpotifyNavType>, showAlbumDetailState: MutableState<Album?>) {
    Column(
        modifier = Modifier.fillMaxHeight().preferredWidth(250.dp).background(spotifyBlack).padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            asset = imageFromResource("spotify.png"),
            modifier = Modifier.padding(end = 16.dp, top = 16.dp, bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        SideBarNavItem("Home", Icons.Default.Home, spotifyNavItemState.value == SpotifyNavType.HOME) {
            spotifyNavItemState.value = SpotifyNavType.HOME
            showAlbumDetailState.value = null
        }
        SideBarNavItem("Search", Icons.Default.Search, spotifyNavItemState.value == SpotifyNavType.SEARCH) {
            spotifyNavItemState.value = SpotifyNavType.SEARCH
            showAlbumDetailState.value = null
        }
        SideBarNavItem("Your Library", Icons.Default.List, spotifyNavItemState.value == SpotifyNavType.LIBRARY) {
            spotifyNavItemState.value = SpotifyNavType.LIBRARY
            showAlbumDetailState.value = null
        }

        Spacer(modifier = Modifier.height(20.dp))
        PlayListsSideBar {
            val randomAlbum = SpotifyDataProvider.albums.random()
            showAlbumDetailState.value = randomAlbum
        }
    }
}

@Composable
fun PlayerBottomBar(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = graySurface)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            asset = imageFromResource("adele.jpeg"),
            modifier = Modifier.preferredSize(75.dp).padding(8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Someone Like you by Adele",
            style = typography.h6.copy(fontSize = 14.sp),
            color = Color.White,
            modifier = Modifier.padding(16.dp),
        )
        Icon(asset = Icons.Default.AddCircle, modifier = Modifier.padding(8.dp), tint = Color.White)
        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
            Icon(asset = Icons.Default.Refresh, modifier = Modifier.padding(8.dp), tint = Color.White)
            Icon(asset = Icons.Default.PlayArrow, modifier = Modifier.padding(8.dp), tint = Color.White)
            Icon(asset = Icons.Default.Favorite, modifier = Modifier.padding(8.dp), tint = spotifyGreen)
        }

        Icon(asset = Icons.Default.List, modifier = Modifier.padding(8.dp), tint = Color.White)
        Icon(asset = Icons.Default.Share, modifier = Modifier.padding(8.dp), tint = Color.White)
    }
}

@Composable
fun PlayListsSideBar(onPlayListSelected: () -> Unit) {
    Text("PLAYLISTS", modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp), color = Color.White)
    LazyColumnFor(items = SpotifyDataProvider.playLits) {
        Text(it, modifier = Modifier.padding(8.dp).clickable { onPlayListSelected.invoke() }, color = Color.LightGray)
    }
}


@Composable
fun SideBarNavItem(title: String, icon: VectorAsset, selected: Boolean, onClick: () -> Unit) {
    val animatedBackgroundColor = animate(if (selected) graySurface else spotifyBlack)
    val animatedContentColor = animate(if (selected) Color.White else Color.LightGray)
    Row(
        modifier = Modifier
            .fillMaxWidth().background(animatedBackgroundColor).clip(RoundedCornerShape(4.dp)).padding(16.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Icon(asset = icon, tint = animatedContentColor)
        Text(
            title,
            style = typography.body1,
            color = animatedContentColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}