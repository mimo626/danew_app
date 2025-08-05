package com.example.danew_app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danew.presentation.home.Article
import com.example.danew_app.core.theme.ColorsLight

// 가로로 뉴스 카드(제목만)
@Composable
fun NewsCard(article: Article) {
    Box(
        modifier = Modifier
            .height(100.dp)
            .width(224.dp)
            .padding(end = 16.dp)
            .background(ColorsLight.lightGrayColor, shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Column (modifier = Modifier.padding(horizontal = 16.dp)){
            Text(article.title, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(8.dp))
            Text(article.timeAgo, color = Color.Gray, fontSize = 12.sp)
        }
    }
}