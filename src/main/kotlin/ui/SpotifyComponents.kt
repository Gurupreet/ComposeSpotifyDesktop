package ui

import androidx.compose.animation.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asDesktopBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.composecookbook.ui.demoui.spotify.data.Album
import graySurface
import org.jetbrains.skija.Bitmap
import spotifyGreen
import utils.horizontalGradientBackground

@Composable
fun SpotifyHomeGridItem(album: Album) {
    val cardColor = graySurface
    Card(
        elevation = 4.dp,
        backgroundColor = cardColor,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable(onClick = {})
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageFromResource(album.imageId),
                modifier = Modifier.preferredSize(55.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = album.song,
                style = typography.h6.copy(fontSize = 14.sp),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun SpotifyLaneItem(album: Album, onclick: () -> Unit) {
    val album = remember { album }
    var showPlayButton by remember { mutableStateOf(false) }
    var scalePlayButton by remember { mutableStateOf(false) }

    Box(modifier = Modifier.pointerMoveFilter(
        onEnter = {
            showPlayButton = true
            false
        },
        onExit = {
            showPlayButton = false
            false
        })
        .clickable { onclick.invoke() }
    ) {
        Column(
            modifier = Modifier
                .preferredWidth(240.dp).padding(24.dp)
        ) {
            Image(
                imageFromResource(album.imageId),
                modifier = Modifier.preferredWidth(240.dp)
                    .preferredHeight(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "${album.song}: ${album.descriptions}",
                style = typography.body2,
                maxLines = 2,
                color = MaterialTheme.colors.onSurface,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        IconButton(
            onClick = {},
            modifier = Modifier
                .pointerMoveFilter(
                    onEnter = {
                        scalePlayButton = true
                        false
                    },
                    onExit = {
                        scalePlayButton = false
                        false
                    }
                )
                .alpha(animate(if (showPlayButton) 1f else 0f, animSpec = tween(500)))
                .align(Alignment.BottomEnd)
                .padding(bottom = 90.dp, end = 30.dp)
                .clip(CircleShape)
                .graphicsLayer(
                    scaleX = animate(if (scalePlayButton) 1.3f else 1f),
                    scaleY = animate(if (scalePlayButton) 1.3f else 1f)
                )
                .background(spotifyGreen)
        ) {
            Icon(Icons.Default.PlayArrow, tint = MaterialTheme.colors.onSurface)
        }
    }
}

@Composable
fun SpotifySearchGridItem(album: Album, modifier: Modifier = Modifier, onclick: () -> Unit) {
    val dominantColor = remember(album.id) { getDominantColor(imageFromResource(album.imageId).asDesktopBitmap()) }
    val dominantGradient = remember(album.id) { listOf(dominantColor, dominantColor.copy(alpha = 0.6f)) }

    Row(
        modifier = modifier
            .padding(12.dp)
            .clickable(onClick = { onclick.invoke() })
            .preferredHeight(220.dp)
            .clip(RoundedCornerShape(8.dp))
            .horizontalGradientBackground(dominantGradient),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = album.song,
            style = typography.h6,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(8.dp)
        )
        Image(
            imageFromResource(album.imageId),
            contentScale = ContentScale.Crop,
            modifier = Modifier.preferredSize(100.dp)
                .align(Alignment.Bottom)
                .drawLayer(translationX = 40f, rotationZ = 32f, shadowElevation = 16f)
        )
    }
}


fun getDominantColor(bitmap: Bitmap): Color {
    val colorCode = bitmap.getColor(10, 10)
    return Color(colorCode)
}
