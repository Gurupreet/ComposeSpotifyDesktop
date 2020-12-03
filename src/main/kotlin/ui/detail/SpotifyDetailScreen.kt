package ui.detail

import androidx.compose.animation.animate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asDesktopBitmap
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.guru.composecookbook.ui.demoui.spotify.data.Album
import com.guru.composecookbook.ui.demoui.spotify.data.SpotifyDataProvider
import spotifyGreen
import ui.getDominantColor
import utils.horizontalGradientBackground
import utils.verticalGradientBackground

@Composable
fun SpotifyDetailScreen(album: Album, onBack: () -> Unit) {
    val album = remember(album.id) { album }
    val scrollState = rememberScrollState(0f)
    val dominantColor = remember(album.id) { getDominantColor(imageFromResource(album.imageId).asDesktopBitmap()) }
    val surface = MaterialTheme.colors.surface
    val dominantGradient = remember(album.id) { listOf(dominantColor, surface) }
    val surfaceGradient =
        remember(album.id) { listOf(dominantColor, surface, surface, surface, surface) }
    Box(modifier = Modifier.fillMaxSize().verticalGradientBackground(dominantGradient)) {
        TopAlbumInfoSection(album = album, scrollState = scrollState)
        TopAlbumInfoOverlay(scrollState = scrollState)

        SongsScrollableContent(scrollState = scrollState, surfaceGradient = surfaceGradient)
        AnimatedToolBar(album, scrollState, dominantColor, onBack)
    }
}

@Composable
fun AnimatedToolBar(album: Album, scrollState: ScrollState, dominantColor: Color, onBack: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalGradientBackground(
                if (Dp(scrollState.value) < 700.dp)
                    listOf(Color.Transparent, Color.Transparent) else listOf(dominantColor, dominantColor)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.clickable { onBack.invoke() })
        Text(
            text = album.song,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .padding(16.dp)
                .alpha(((scrollState.value + 0.001f) / 1000).coerceIn(0f, 1f))
        )
        Icon(imageVector = Icons.Default.MoreVert, tint = MaterialTheme.colors.onSurface)
    }
}

@Composable
fun TopAlbumInfoOverlay(scrollState: ScrollState) {
    //slowly increase alpha till it reaches 1
    val dynamicAlpha = ((scrollState.value + 0.00f) / 1000).coerceIn(0f, 1f)
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(380.dp)
            .background(MaterialTheme.colors.surface.copy(alpha = animate(dynamicAlpha)))
    )
}

@Composable
fun SongsScrollableContent(scrollState: ScrollState, surfaceGradient: List<Color>) {
    ScrollableColumn(scrollState = scrollState, modifier = Modifier) {
        Spacer(modifier = Modifier.height(380.dp))
        Column(modifier = Modifier.verticalGradientBackground(surfaceGradient)) {
            SongListScrollingSection()
        }
    }
}

@Composable
fun SongListScrollingSection() {
    PlayButtons()
    val items = remember { SpotifyDataProvider.albums }
    items.forEach {
        SpotifySongListItem(album = it)
    }
    Spacer(modifier = Modifier.height(100.dp))
}

@Composable
fun PlayButtons() {
    Row(modifier = Modifier.padding(16.dp)) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .padding(bottom = 16.dp, end = 24.dp)
                .clip(CircleShape)
                .background(spotifyGreen)
        ) {
            Icon(Icons.Default.PlayArrow, tint = MaterialTheme.colors.onSurface)
        }
        Icon(imageVector = Icons.Default.Favorite, tint = spotifyGreen, modifier = Modifier.padding(16.dp))
        Icon(
            imageVector = Icons.Default.MoreVert,
            tint = MaterialTheme.colors.onSecondary,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun TopAlbumInfoSection(album: Album, scrollState: ScrollState) {
    Row(modifier = Modifier.padding(top = 100.dp, start = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        //animate as scroll value increase but not fast so divide by random number 50
        val dynamicValue = (250.dp - Dp(scrollState.value / 10)).coerceIn(10.dp, 250.dp)
        val animateImageSize = animate(dynamicValue)
        Image(
            imageFromResource(album.imageId),
            modifier = Modifier
                .preferredSize(animateImageSize)
                .padding(8.dp)
        )
        Column {
            Text(
                text = album.song,
                style = typography.h2.copy(fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = album.descriptions,
                style = typography.subtitle2,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = album.genre,
                style = typography.subtitle2,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )
        }
    }
}