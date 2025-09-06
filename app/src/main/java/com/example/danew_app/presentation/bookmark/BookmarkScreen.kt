package com.example.danew.presentation.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.CustomLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.home.NewsItem
import com.example.danew_app.presentation.viewmodel.BookmarkViewModel

@Composable
fun BookmarkScreen(navController: NavHostController) {
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    // StateFlow를 Compose에서 관찰
    val bookmarkedNewsList by bookmarkViewModel.bookmarkedNewsList.collectAsState()
    val isLoading by bookmarkViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        bookmarkViewModel.getBookmarks()
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(
                navController = navController,
                title = "북마크",
                icon = Icons.Default.Notifications,
                isHome = false
            ) {
                navController.navigate("alarm")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            when {
                isLoading -> {
                    item {
                        CustomLoadingIndicator(padding)
                    }
                }
                bookmarkedNewsList.isNotEmpty() -> {
                    items(bookmarkedNewsList) { news ->
                        NewsItem(newsModel = news, navController = navController)
                    }
                }
                else -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(padding),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("북마크가 없습니다")
                        }
                    }
                }
            }
        }
    }
}
