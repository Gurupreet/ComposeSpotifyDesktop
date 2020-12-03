package ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.composecookbook.ui.demoui.spotify.data.Album


@Composable
fun SpotifySongListItem(album: Album) {
    val album = remember(album.id) { album }
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageFromResource(album.imageId),
            contentScale = ContentScale.Crop,
            modifier = Modifier.preferredSize(55.dp).padding(4.dp)
        )
        Column(modifier = Modifier.padding(horizontal = 4.dp).weight(1f)) {
            Text(
                text = album.song,
                style = typography.h6.copy(fontSize = 16.sp),
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "${album.artist}, ${album.descriptions}",
                style = typography.subtitle2,
                maxLines = 1,
                color = MaterialTheme.colors.onSecondary,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (album.id % 3 == 0) {
            Icon(
                imageVector = Icons.Default.Favorite,
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier.padding(4.dp).preferredSize(20.dp)
            )
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            tint = MaterialTheme.colors.onSecondary,
            modifier = Modifier.padding(4.dp)
        )
    }
}