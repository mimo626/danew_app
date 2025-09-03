package com.example.danew_app.core.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.danew_app.core.theme.ColorsLight

@Composable
fun LazyItemScope.CustomLoadingIndicator(
    padding: PaddingValues = PaddingValues(0.dp)
) {
    Box(
        modifier = Modifier
            .fillParentMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = ColorsLight.grayColor)
    }
}

