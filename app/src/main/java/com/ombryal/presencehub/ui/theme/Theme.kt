package com.ombryal.presencehub.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ombryal.presencehub.ui.settings.ThemeMode

private val DarkColors = darkColorScheme()
private val LightColors = lightColorScheme()

val LocalThemeMode = staticCompositionLocalOf { ThemeMode.SYSTEM }

@Composable
fun PresenceHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val useDarkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> darkTheme
        ThemeMode.DARK, ThemeMode.GLASS -> true
        ThemeMode.LIGHT -> false
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (useDarkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        useDarkTheme -> DarkColors
        else -> LightColors
    }

    CompositionLocalProvider(LocalThemeMode provides themeMode) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (themeMode == ThemeMode.GLASS) {
                    GlassmorphismBackground()
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorScheme.background)
                    )
                }
                content()
            }
        }
    }
}
