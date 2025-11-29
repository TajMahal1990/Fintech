package com.achievemeaalk.freedjf.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils



// ----------------------------------------------------
// A5 DARK THEME (PREMIUM CRYPTO STYLE)
// ----------------------------------------------------

val DarkPrimary = Color(0xFF14D3A5)        // Neon Turquoise
val DarkOnPrimary = Color.White

val DarkBackground = Color(0xFF0B0C0F)     // Main Black Background
val DarkSurface = Color(0xFF121416)        // Glass-like Dark Surface

val DarkOnSurface = Color(0xFFEFEFEF)      // Almost White
val DarkOnSurfaceVariant = Color(0xFFFFFFFF) // Soft Gray Text

val DarkError = Color(0xFFFF453A)


// ----------------------------------------------------
// A5 LIGHT THEME (STILL DARK — TO KEEP PREMIUM LOOK)
// ----------------------------------------------------

val LightPrimary = Color(0xFF14D3A5)
val LightOnPrimary = Color.White

val LightBackground = Color(0xFF0B0C0F)     // SAME AS DARK → Premium feel
val LightSurface = Color(0xFF121416)

val LightOnSurface = Color(0xFFEFEFEF)
val LightOnSurfaceVariant = Color(0xFF9BA3AE)

val LightError = Color(0xFFFF3B30)


// ----------------------------------------------------
// Semantic Colors (Keep but tint to neon crypto style)
// ----------------------------------------------------

val IncomeColor = Color(0xFF14D3A5)
val OnIncomeColor = Color(0xFF0A7F63)

val ExpenseColor = Color(0xFFFF3B30)
val OnExpenseColor = Color(0xFF8C1E16)

val TransferColor = Color(0xFF1DA1F2)

val BudgetSpent = Color(0xFF1DA1F2)
val BudgetRemaining = Color(0xFF14D3A5)

val InactiveIndicator = Color(0x33000000)
val InactiveOnboardingIndicator = Color(0x4DEFEFEF)

val DisabledBorder = Color(0x4D14D3A5)
val WarningColor = Color(0xFFFF9F0A)

val ProgressBarError = Color(0x4DFF3B30)

val SurfaceVariant70 = Color(0xB3121416)
val OnSurface70 = Color(0xFFFFFFFF)
val OnSurface80 = Color(0xFFFFFFFF)

val Success = Color(0xFF14D3A5)
val OnSuccess = Color.White


// ----------------------------------------------------
// Category & Chart Colors (Neon Crypto Style)
// ----------------------------------------------------
val ChartGray = Color(0xFF2A2D31)

val ChartColors = listOf(
    Color(0xFF14D3A5), // Neon Turquoise
    Color(0xFF1DA1F2), // Blue
    Color(0xFFFF9F0A), // Orange
    Color(0xFFFF453A), // Red
    Color(0xFF9D4EDD), // Purple
    Color(0xFF4FD1C5), // Teal
    Color(0xFFFFD60A), // Yellow
    Color(0xFF8E8D89), // Grayish
    Color(0xFF5E60CE), // Indigo
    Color(0xFFFF4D6D), // Pink
    Color(0xFF40C8E0), // Cyan
    Color(0xFF7B8AFF)  // Lavender
)

val CategoryColors = ChartColors


// ----------------------------------------------------
// Utils
// ----------------------------------------------------

fun Color.isColorDark(): Boolean {
    val hsl = FloatArray(3)
    ColorUtils.colorToHSL(this.hashCode(), hsl)
    return hsl[2] < 0.5f
}
