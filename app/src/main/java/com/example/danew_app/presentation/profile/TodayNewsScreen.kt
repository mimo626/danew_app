package com.example.danew_app.presentation.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.CustomLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.presentation.home.MainImageCard
import com.example.danew_app.presentation.home.NewsItem
import com.example.danew_app.presentation.home.NewsList
import com.example.danew_app.presentation.home.NowTopNews
import com.example.danew_app.presentation.home.SearchBar
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import com.example.danew_app.presentation.viewmodel.TodayNewsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayNewsScreen(navHostController: NavHostController) {
    val todayNewsViewModel: TodayNewsViewModel = hiltViewModel()
    val todayNews by todayNewsViewModel.todayNews.collectAsState()
    val isLoading = todayNewsViewModel.isLoading

    LaunchedEffect(Unit) {
        todayNewsViewModel.getNews()
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(
                navController = navHostController,
                title = "오늘 본 뉴스",
                isBackIcon = true
            )
        }
    ) { padding ->
        // 실제 화면
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isLoading) {
                // 로딩 화면
                item{
                    CustomLoadingIndicator(padding)
                }
            }
            else {
                item { Spacer(modifier = Modifier.height(16.dp)) }
                if (todayNews.isNotEmpty()) {
                    items(todayNews){
                        NewsItem(it.toDomain(), navController = navHostController)

                    }
                    item { Spacer(modifier = Modifier.height(28.dp)) }
                }
            }
        }

    }
}


