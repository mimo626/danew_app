package com.example.danew_app.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.domain.model.NewsModel

//홈의 첫번째 이미지 카드
@Composable
fun MainImageCard(newsModel: NewsModel, navController:NavHostController) {
    Column (modifier = Modifier.clickable{
        navController.navigate("details/${newsModel.newsId}")
    }){
        Image(
            painter = rememberAsyncImagePainter(newsModel.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ColorsLight.darkGrayColor)
        ){
            Text(newsModel.title, fontWeight = FontWeight.Bold, fontSize = 14.sp,
                color = ColorsLight.whiteColor,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}