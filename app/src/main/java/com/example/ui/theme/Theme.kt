package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ZonexDarkColorScheme = darkColorScheme(
    primary = ZonexRedPrimary,
    onPrimary = ZonexPureBlack,
    primaryContainer = ZonexRedDark,
    onPrimaryContainer = ZonexGreyLight,
    secondary = ZonexGold,
    onSecondary = ZonexPureBlack,
    tertiary = ZonexGreenSafe,
    onTertiary = ZonexPureBlack,
    background = ZonexPureBlack,
    onBackground = ZonexGreyLight,
    surface = ZonexCardSurface,
    onSurface = ZonexGreyLight,
    surfaceVariant = ZonexCardSurfaceElevated,
    onSurfaceVariant = ZonexGreyText,
    outline = ZonexBorderDark,
    outlineVariant = ZonexRedSubtleBorder
)

@Composable
fun ZonexTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = ZonexPureBlack.toArgb()
            window.navigationBarColor = ZonexPureBlack.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = ZonexDarkColorScheme,
        typography = ZonexTypography,
        content = content
    )
}
