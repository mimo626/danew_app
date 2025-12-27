package com.example.danew_app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.danew_app.core.theme.DanewColors

private val LightColorScheme = lightColorScheme(
    primary = DanewColors.primaryColor,
    onPrimary = DanewColors.whiteColor,
    secondary = DanewColors.lightGrayColor,
    onSecondary = DanewColors.blackColor,
    background = DanewColors.whiteColor,
    onBackground = DanewColors.blackColor,
    surface = DanewColors.lightGrayColor,
    onSurface = DanewColors.darkGrayColor,
    error = DanewColors.whiteColor,
    onError = DanewColors.redColor,
)

private val DarkColorScheme = darkColorScheme(
    primary = DanewColors.primaryColor,
    onPrimary = DanewColors.whiteColor,
    secondary = DanewColors.darkGrayColor,
    onSecondary = DanewColors.whiteColor,
    background = DanewColors.blackColor,
    onBackground = DanewColors.whiteColor,
    surface = DanewColors.darkGrayColor,
    onSurface = DanewColors.grayColor,
    error = DanewColors.darkGrayColor,
    onError = DanewColors.redColor,
)

@Composable
fun Danew_appTheme(
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}