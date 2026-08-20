package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4A6B3D),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFCBE2B9),
    onPrimaryContainer = Color(0xFF0F2007),
    secondary = Color(0xFF8B5A2B),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDDBA),
    onSecondaryContainer = Color(0xFF2C1600),
    tertiary = Color(0xFFD67229),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDBC6),
    onTertiaryContainer = Color(0xFF321200),
    background = Color(0xFFFAFAFA),
    onBackground = Color(0xFF191C19),
    surface = Color(0xFFFAFAFA),
    onSurface = Color(0xFF191C19),
    surfaceVariant = Color(0xFFE2E3DD),
    onSurfaceVariant = Color(0xFF43483E)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFAFD59F),
    onPrimary = Color(0xFF1B3712),
    primaryContainer = Color(0xFF325026),
    onPrimaryContainer = Color(0xFFCBE2B9),
    secondary = Color(0xFFE9BF94),
    onSecondary = Color(0xFF422B08),
    secondaryContainer = Color(0xFF5E411D),
    onSecondaryContainer = Color(0xFFFFDDBA),
    tertiary = Color(0xFFFFB68C),
    onTertiary = Color(0xFF4F2500),
    tertiaryContainer = Color(0xFF703700),
    onTertiaryContainer = Color(0xFFFFDBC6),
    background = Color(0xFF191C19),
    onBackground = Color(0xFFE2E3DD),
    surface = Color(0xFF191C19),
    onSurface = Color(0xFFE2E3DD),
    surfaceVariant = Color(0xFF43483E),
    onSurfaceVariant = Color(0xFFC3C8BC)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
