package com.example.danew_app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danew_app.domain.model.NewsModel

// 가로로 뉴스 카드(제목만)
@Composable
fun NewsCard(newsModel: NewsModel, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .width(220.dp)
            .padding(end = 16.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(4.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Column (modifier = Modifier.padding(16.dp)){
            Text(newsModel.title, maxLines = 2, fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondary,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text((newsModel.pubDate), color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
        }
    }
}