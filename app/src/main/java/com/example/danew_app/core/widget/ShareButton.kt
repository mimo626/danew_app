package com.example.danew_app.core.widget

import android.content.Intent
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ShareButton(newsLink:String?){
    val context = LocalContext.current

    IconButton(onClick = {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${newsLink}")
            type = "text/plain"
        }

        // 시스템 공유 창 열기
        val shareIntent = Intent.createChooser(sendIntent, "공유하기")
        context.startActivity(shareIntent)
    }) {
        Icon(
            tint = MaterialTheme.colorScheme.tertiary,
            imageVector = Icons.Default.Share,
            contentDescription = "공유",
            modifier = Modifier.size(32.dp)
        )
    }
}