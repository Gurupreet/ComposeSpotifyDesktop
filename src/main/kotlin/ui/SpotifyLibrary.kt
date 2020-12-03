package ui

import androidx.compose.animation.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.unit.dp
import utils.horizontalGradientBackground

@Composable
fun SpotifyLibrary() {
    ScrollableColumn(
        modifier = Modifier
            .fillMaxSize()
            .horizontalGradientBackground(listOf(MaterialTheme.colors.secondary, MaterialTheme.colors.surface))
            .padding(vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var draw2 by remember { mutableStateOf(false) }
        var draw4 by remember { mutableStateOf(false) }
        val imageSize = 300.dp

        SpotifyTitle("My Library")
        Spacer(modifier = Modifier.padding(30.dp))
        Box(modifier = Modifier.clickable(onClick = {
            draw2 = !draw2
            draw4 = false
        })) {
            Image(
                imageFromResource("bp.jpg"),
                modifier = Modifier.preferredSize(imageSize).drawLayer(
                    shadowElevation = animate(if (draw2) 30f else 5f),
                    translationX = animate(target = if (draw2) 520f else 0f),
                    translationY = 0f,
                )
            )
            Image(
                imageFromResource("dualipa.jpeg"),
                modifier = Modifier.preferredSize(imageSize).drawLayer(
                    shadowElevation = animate(if (draw2) 30f else 10f),
                    translationX = animate(target = if (draw2) -520f else 0f),
                    translationY = animate(target = if (draw2) 0f else 30f)
                ).clickable(onClick = { draw2 = !draw2 })
            )
            Image(
                imageFromResource("tylor.jpeg"),
                modifier = Modifier.preferredSize(imageSize).drawLayer(
                    shadowElevation = animate(if (draw2) 30f else 5f),
                    translationY = animate(target = if (draw2) 0f else 50f)
                ).clickable(onClick = { draw2 = !draw2 })
            )
        }

        Spacer(modifier = Modifier.padding(50.dp))

        Box(modifier = Modifier.clickable {
            draw4 = !draw4
            draw2 = false
        }) {
            Image(
                imageFromResource("katy.jpg"),
                modifier = Modifier.preferredSize(imageSize).drawLayer(
                    shadowElevation = animate(if (draw4) 30f else 5f),
                    translationX = animate(target = if (draw4) 320f else 0f),
                    rotationZ = animate(target = if (draw4) 45f else 0f),
                    translationY = 0f
                )
            )
            Image(
                imageFromResource("ed2.jpg"),
                modifier = Modifier.preferredSize(imageSize).drawLayer(
                    shadowElevation = animate(if (draw4) 30f else 10f),
                    translationX = animate(target = if (draw4) -320f else 0f),
                    rotationZ = animate(target = if (draw4) 45f else 0f),
                    translationY = animate(target = if (draw4) 0f else 30f)
                )
            )
            Image(
                imageFromResource("camelia.jpeg"),
                modifier = Modifier.preferredSize(imageSize).drawLayer(
                    shadowElevation = animate(if (draw4) 30f else 5f),
                    translationY = animate(target = if (draw4) 0f else 50f),
                    rotationZ = animate(target = if (draw4) 45f else 0f)
                )
            )
        }
    }
}