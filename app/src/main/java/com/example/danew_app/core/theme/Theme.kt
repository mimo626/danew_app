package com.example.myapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.danew_app.core.theme.ColorsDark
import com.example.danew_app.core.theme.ColorsLight

private val LightColorScheme = lightColorScheme(
    primary = ColorsLight.primaryColor,
    onPrimary = ColorsLight.whiteColor,
    secondary = ColorsLight.whiteColor,
    onSecondary = ColorsLight.blackColor,
    background = ColorsLight.whiteColor,
    onBackground = ColorsLight.blackColor,
    surface = ColorsLight.whiteColor,
    onSurface = ColorsLight.blackColor,
    error = ColorsLight.redColor,
    onError = ColorsLight.whiteColor,
)

private val DarkColorScheme = darkColorScheme(
    primary = ColorsDark.primaryColor,
    onPrimary = ColorsDark.blackColor,
    secondary = ColorsDark.lightGrayColor,
    onSecondary = ColorsDark.whiteColor,
    background = ColorsDark.darkGrayColor,
    onBackground = ColorsDark.whiteColor,
    surface = ColorsDark.grayColor,
    onSurface = ColorsDark.whiteColor,
    error = ColorsDark.redColor,
    onError = ColorsDark.blackColor,
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // 시스템 설정에 따라 자동 전환
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
//        typography = Typography,
        content = content
    )
}
