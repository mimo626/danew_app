package com.example.danew_app.core.widget

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.danew_app.core.theme.ColorsLight

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
            tint = ColorsLight.darkGrayColor,
            imageVector = Icons.Default.Share,
            contentDescription = "공유"
        )
    }
}