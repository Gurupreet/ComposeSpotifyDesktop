package ui.detail

import androidx.compose.animation.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asDesktopBitmap
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.composecookbook.ui.demoui.spotify.data.Album
import com.guru.composecookbook.ui.demoui.spotify.data.SpotifyDataProvider
import graySurface
import spotifyBlack
import spotifyGreen
import ui.getDominantColor
import utils.horizontalGradientBackground
import utils.verticalGradientBackground

@Composable
fun SpotifyDetailScreen(album: Album) {
    val album = remember { album }
    val scrollState = rememberScrollState(0f)
    val dominantColor = remember(album.id) { getDominantColor(imageFromResource(album.imageId).asDesktopBitmap()) }
    val dominantGradient = remember(album.id) { listOf(dominantColor, spotifyBlack) }
    val surfaceGradient = remember { listOf(dominantColor,spotifyBlack,spotifyBlack, spotifyBlack, spotifyBlack) }
    Box(modifier = Modifier.fillMaxSize().verticalGradientBackground(dominantGradient)) {
        TopAlbumInfoSection(album = album, scrollState = scrollState)
        TopAlbumInfoOverlay(scrollState = scrollState)

        SongsScrollableContent(scrollState = scrollState, surfaceGradient = surfaceGradient)
        AnimatedToolBar(album, scrollState, dominantColor)
    }
}

@Composable
fun AnimatedToolBar(album: Album, scrollState: ScrollState, dominantColor: Color) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalGradientBackground(
                if (Dp(scrollState.value) < 1080.dp)
                    listOf(Color.Transparent, Color.Transparent) else listOf(dominantColor, dominantColor)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(asset = Icons.Default.ArrowBack, tint = Color.White)
        Text(
            text = album.song,
            color = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .drawOpacity(((scrollState.value + 0.001f) / 1000).coerceIn(0f, 1f))
        )
        Icon(asset = Icons.Default.MoreVert, tint = Color.White)
    }
}

@Composable
fun TopAlbumInfoOverlay(scrollState: ScrollState) {
    //slowly increase alpha till it reaches 1
    val dynamicAlpha = ((scrollState.value + 0.00f) / 1000).coerceIn(0f, 1f)
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(380.dp)
            .background(spotifyBlack.copy(alpha = animate(dynamicAlpha)))
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
}

@Composable
fun PlayButtons() {
    Row(modifier = Modifier.padding(16.dp)) {
        IconButton(
            onClick = {},
            icon = { Icon(Icons.Default.PlayArrow, tint = Color.White) },
            modifier = Modifier
                .padding(bottom = 16.dp, end = 24.dp)
                .clip(CircleShape)
                .background(spotifyGreen)
        )
        Icon(asset = Icons.Default.Favorite, tint = spotifyGreen, modifier = Modifier.padding(16.dp))
        Icon(asset = Icons.Default.MoreVert, tint = Color.LightGray, modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun TopAlbumInfoSection(album: Album, scrollState: ScrollState) {
    Row(modifier = Modifier.padding(top = 100.dp, start = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        //animate as scroll value increase but not fast so divide by random number 50
        val dynamicValue = (250.dp - Dp(scrollState.value / 10)).coerceIn(10.dp, 250.dp)
        val animateImageSize = animate(dynamicValue)
        Image(
            asset = imageFromResource(album.imageId),
            modifier = Modifier
                .preferredSize(animateImageSize)
                .padding(8.dp)
        )
        Column {
            Text(
                text = album.genre,
                style = typography.subtitle2,
                color = Color.LightGray,
                modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
            )
            Text(
                text = album.song,
                style = typography.h2.copy(fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
            Text(
                text = album.descriptions,
                style = typography.subtitle2,
                color = Color.LightGray,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}