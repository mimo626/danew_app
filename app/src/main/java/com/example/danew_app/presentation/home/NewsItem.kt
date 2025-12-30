package com.example.danew_app.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.danew_app.domain.model.NewsModel

// 가로로 뉴스 카드(작은 사진)
@Composable
fun NewsItem(newsModel: NewsModel, onItemClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp)
            .clickable{
                onItemClick()
            }
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), // 높이를 꽉 채워야 양 끝으로 벌어집니다.
            verticalArrangement = Arrangement.SpaceBetween // 높이를 꽉 채워야 양 끝으로 벌어집니다.
        ) {
            Text(newsModel.title, maxLines = 2,
                minLines = 2,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondary,
                overflow = TextOverflow.Ellipsis
            )
            Text((newsModel.pubDate), color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.width(16.dp))
        if(newsModel.imageUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(newsModel.imageUrl)
                    .listener(
                        onError = { request, result ->
                            Log.e("ImageLoad", "Failed to load: ${newsModel.imageUrl}")
                        },
                        onSuccess = { request, result ->
                            Log.d("ImageLoad", "Success: ${newsModel.imageUrl}")
                        }
                    )
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
        }
        else {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Warning,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    contentDescription = "썸네일",)
            }
        }
    }
}