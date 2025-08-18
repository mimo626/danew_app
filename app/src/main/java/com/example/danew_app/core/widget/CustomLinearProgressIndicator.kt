package com.example.danew_app.core.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.danew_app.core.theme.ColorsLight

@Composable
fun CustomLinearProgressIndicator (progress:Float){
    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(4.dp)),
        color = ColorsLight.primaryColor,
        trackColor = ColorsLight.lightGrayColor,)
}