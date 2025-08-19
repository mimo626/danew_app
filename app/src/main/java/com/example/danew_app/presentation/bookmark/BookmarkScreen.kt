package com.example.danew.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.danew_app.core.gloabals.Globals
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.home.NewsItem

@Composable
fun BookmarkScreen(navController: NavHostController,) {
    Scaffold (
        containerColor = ColorsLight.whiteColor,
        topBar =  {
            MainTopAppBar(
                navController = navController,
                title = "북마크",
                icon = Icons.Default.Notifications,
                isHome = false,
            ) {
                navController.navigate("alarm")
            }
        }
    ){
            padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // 뉴스 리스트
            LazyColumn {
                items(Globals.dummyNewsList) { news ->
                    NewsItem(newsModel = news, navController = navController)
                }
            }
        }
    }
}