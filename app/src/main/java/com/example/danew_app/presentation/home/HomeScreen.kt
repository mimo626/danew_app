package com.example.danew.presentation.home
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.gloabals.Globals
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.domain.model.UserModel
import com.example.danew_app.presentation.home.MainImageCard
import com.example.danew_app.presentation.home.NewsList
import com.example.danew_app.presentation.home.NowTopNews
import com.example.danew_app.presentation.home.SearchBar
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import com.example.danew_app.presentation.viewmodel.UserViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val newsViewModel: NewsViewModel = hiltViewModel()

    val mainImageNews = newsViewModel.mainImageNews
    val recommendedNews = newsViewModel.recommendedNews
    val keywordNews = newsViewModel.keywordNews
    val isLoading = newsViewModel.isLoading

    LaunchedEffect(Unit) {
        newsViewModel.fetchCustomNews()
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(
                navController = navController,
                title = "DANEW",
                icon = Icons.Default.Notifications,
                isHome = true
            ) {
                navController.navigate("alarm")
            }
        }
    ) { padding ->
        if (isLoading) {
            // 로딩 화면
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = ColorsLight.grayColor)
            }
        } else {
            // 실제 화면
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                item { Spacer(modifier = Modifier.height(16.dp)) }

                item { SearchBar(navHostController = navController) }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                // MainImageCard
                mainImageNews?.let {
                    item { MainImageCard(it, navController) }
                }

                item { Spacer(modifier = Modifier.height(40.dp)) }

                // 추천 뉴스
                if (recommendedNews.isNotEmpty()) {
                    item { NewsList("민주님을 위한 추천 뉴스", recommendedNews, navController) }
                    item { Spacer(modifier = Modifier.height(28.dp)) }
                }

                // 키워드별 TOP 뉴스
                keywordNews.forEach { (keyword, list) ->
                    if (list.isNotEmpty()) {
                        item { NowTopNews("현재 TOP $keyword 뉴스", list, navController) }
                        item { Spacer(modifier = Modifier.height(28.dp)) }
                    }
                }
            }
        }
    }
}


