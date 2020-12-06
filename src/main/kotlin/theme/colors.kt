import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val graySurface = Color(0xFF2A2A2A)
val lightGray = Color(0xFFD3D3D3)
val spotifyGreen = Color(0xFF1db954)
val green700 = Color(0xff388e3c)
val spotifyBlack = Color(0xff100c08)
val darkGray = Color(0xFF565656)
val spotifyGradient = listOf(spotifyGreen, Color.Yellow, spotifyGreen.copy(alpha = 0.8f))

val DarkGreenColorPalette = darkColors(
    primary = green700,
    primaryVariant = green700,
    secondary = graySurface,
    background = spotifyBlack,
    surface = spotifyBlack,
    onPrimary = spotifyBlack,
    onSecondary = lightGray,
    onBackground = Color.White,
    onSurface = Color.White,
    error = Color.Red,
)

val LightGreenColorPalette = lightColors(
    primary = green700,
    primaryVariant = green700,
    secondary = lightGray,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = graySurface,
    onBackground = spotifyBlack,
    onSurface = spotifyBlack
)