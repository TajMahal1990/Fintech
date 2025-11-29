package com.achievemeaalk.freedjf.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat



// ----------------------------------------------------
// DARK THEME — MAIN THEME OF THE APP
// ----------------------------------------------------

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,

    background = DarkBackground,
    surface = DarkSurface,

    onSurface = Color.White,              // FULL WHITE
    onSurfaceVariant = Color.White,        // FULL WHITE

    error = DarkError
)

// ----------------------------------------------------
// LIGHT THEME — SAME COLORS (PREMIUM LOOK)
// ----------------------------------------------------

val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,

    background = LightBackground,
    surface = LightSurface,

    onSurface = Color.White,               // FULL WHITE
    onSurfaceVariant = Color.White,        // FULL WHITE

    error = LightError
)

// ----------------------------------------------------
// EXTENDED COLORS FOR CUSTOM COMPONENTS
// ----------------------------------------------------

val lightExtendedColors = ExtendedColors(
    success = IncomeColor,
    onSuccess = OnIncomeColor,

    warning = WarningColor,
    onWarning = Color.White,

    neutral = ChartGray,
    onNeutral = Color.Black
)

val darkExtendedColors = ExtendedColors(
    success = IncomeColor,
    onSuccess = OnIncomeColor,

    warning = WarningColor,
    onWarning = Color.White,

    neutral = ChartGray,
    onNeutral = Color.Black
)

val LocalExtendedColors = staticCompositionLocalOf {
    lightExtendedColors
}

// ----------------------------------------------------
// SHAPES
// ----------------------------------------------------

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(Dimensions.cornerRadiusSmall),
    small = RoundedCornerShape(Dimensions.cornerRadiusMedium),
    medium = RoundedCornerShape(Dimensions.cornerRadiusLarge),
    large = RoundedCornerShape(Dimensions.cornerRadiusExtraLarge),
    extraLarge = RoundedCornerShape(Dimensions.cornerRadiusExtraLarge)
)

// ----------------------------------------------------
// ACCESSOR
// ----------------------------------------------------

object AppTheme {
    val colors: ExtendedColors
        @Composable get() = LocalExtendedColors.current
}

// ----------------------------------------------------
// MAIN THEME WRAPPER
// ----------------------------------------------------

@Composable
fun MonefyTheme(
    useDarkTheme: Boolean = true,    // FORCE DARK THEME
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (useDarkTheme) darkExtendedColors else lightExtendedColors

    // Change system bars (status + navigation)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()

            // Light icons OFF because theme is dark
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = AppShapes,
            content = content
        )
    }
}
