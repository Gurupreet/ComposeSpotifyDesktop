package com.guru.composecookbook.ui.demoui.spotify.data

import androidx.compose.ui.graphics.Color
import graySurface
import spotifyBlack


object SpotifyDataProvider {

    fun spotifySurfaceGradient(isDark: Boolean) =
        if (isDark) listOf(graySurface, spotifyBlack) else listOf(Color.White, Color.LightGray)

    val listOfSpotifyHomeLanes = listOf(
        "Continue listening",
        "Popular Playlists",
        "Top Charts",
        "Recommended for today",
        "Bollywood",
        "Acoustic only"
    )

    val playLits = listOf(
        "Continue listening",
        "Popular Playlists",
        "Top Charts",
        "Recommended for today",
        "Bollywood",
        "Acoustic only",
        "Continue listening",
        "Popular Playlists",
        "Top Charts",
        "Recommended for today",
        "Bollywood",
        "Acoustic only",
        "Continue listening",
        "Popular Playlists",
        "Top Charts",
        "Recommended for today",
        "Bollywood",
        "Acoustic only",
        "Continue listening",
        "Popular Playlists"
    )

    val album = Album(
        id = 0,
        artist = "Adele",
        song = "Someone like you",
        descriptions = "Album by Adele-2016",
        imageId = "adele.jpeg"
    )

    val albums = mutableListOf(
        Album(
            id = 1,
            artist = "Ed Sheeran",
            song = "Perfect",
            descriptions = "Album by Ed Sheeran-2016",
            imageId = "edsheeran.jpeg",
            genre = "Pop"
        ),
        Album(
            id = 2,
            artist = "Camelia Cabello",
            song = "Havana",
            descriptions = "Album by Camelia Cabello-2016",
            imageId = "camelia.jpeg",
            genre = "R&B"
        ),
        Album(
            id = 3,
            artist = "BlackPink",
            song = "Kill this love",
            descriptions = "Album by BlackPink-2016",
            imageId = "bp.jpg",
            genre = "K-pop"
        ),
        Album(
            id = 4,
            artist = "Ed Sheeran",
            song = "Photograph",
            descriptions = "Album by Ed Sheeran-2016",
            imageId = "ed2.jpg",
            genre = "Acoustic"
        ),
        Album(
            id = 5,
            artist = "Lana del rey",
            song = "Born to die",
            descriptions = "Album by Lana del ray-2014",
            imageId = "lana.jpeg",
            genre = "Jazz"
        ),
        Album(
            id = 6,
            artist = "Khalid",
            song = "Location",
            descriptions = "Album by Khalid-2019",
            imageId = "khalid.png",
            genre = "RnB"
        ),
        Album(
            id = 7,
            artist = "Adele",
            song = "Hello",
            descriptions = "Album by Adele-2019",
            imageId = "adele.jpeg",
            genre = "Pop"
        ),
        Album(
            id = 8,
            artist = "Sam Smith",
            song = "Stay With Me",
            descriptions = "Album by Ed Sheeran-2016",
            imageId = "sam.jpeg",
            genre = "Pop"
        ),
        Album(
            id = 9,
            artist = "Billie Eilish",
            song = "Bad Guy",
            descriptions = "Album by Billie Eilish-2016",
            imageId = "billie.jpg",
            genre = "Pop"
        ),
        Album(
            id = 10,
            artist = "Dua Lipa",
            song = "Break My Heart",
            descriptions = "Album by Dua Lipa-2016",
            imageId = "dualipa.jpeg",
            genre = "Music"
        ),
        Album(
            id = 11,
            artist = "Tones & I",
            song = "Dance Monkey",
            descriptions = "Album by Tones & I-2019",
            imageId = "dancemonkey.jpg",
            genre = "Party"
        ),
        Album(
            id = 12,
            artist = "Marshmello",
            song = "Happier",
            descriptions = "Album by Marshmello-2016",
            imageId = "marsh.jpg",
            genre = "DJ"
        ),
        Album(
            id = 13,
            artist = "Eminem",
            song = "The Eminem Show",
            descriptions = "Album by Eminem-2019",
            imageId = "eminem.jpeg",
            genre = "Rap"
        ),
        Album(
            id = 14,
            artist = "The Weekend",
            song = "Starboy",
            descriptions = "Album by The Weekend-2016",
            imageId = "weekend.jpeg",
            genre = "Mood"
        ),
        Album(
            id = 15,
            artist = "Katy Perry",
            song = "Smile",
            descriptions = "Album by Katy Perry-2016",
            imageId = "katy.jpg",
            genre = "Chill"
        ),
        Album(
            id = 16,
            artist = "Shawn Mendes",
            song = "Senorita",
            descriptions = "Album by Shawn Mendes-2016",
            imageId = "shawn.jpeg",
            genre = "Latin"
        ),
        Album(
            id = 17,
            artist = "Selena Gomez",
            song = "Wolves",
            descriptions = "Album by Selena Gomez-2016",
            imageId = "wolves.jpg",
            genre = "Rock"
        ),
        Album(
            id = 18,
            artist = "Adele",
            song = "Someone Like You",
            descriptions = "Album by Adele 21-2016",
            imageId = "adele21.jpeg",
            genre = "Solo"
        ),
        Album(
            id = 19,
            artist = "Imagine Dragon",
            song = "Believer",
            descriptions = "Album by Imagine Dragon-2017",
            imageId = "imagindragon.jpg",
            genre = "Pop"
        ),
        Album(
            id = 20,
            artist = "James Arthur",
            song = "Impossible",
            descriptions = "Album by James Arthur-2016",
            imageId = "james.jpg",
            genre = "Pop"
        ),
    )
}