package com.example.danew.presentation.home
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.danew_app.core.gloabals.Globals
import com.example.danew_app.domain.model.NewsModel
import com.example.danew_app.presentation.home.MainTopAppBar
import com.example.danew_app.presentation.home.MainImageCard
import com.example.danew_app.presentation.home.NewsList
import com.example.danew_app.presentation.home.NowTopNews
import com.example.danew_app.presentation.home.SearchBar

@Composable
fun HomeScreen() {

    Scaffold(
        topBar = {
            MainTopAppBar(
            title = "DANEW",
            icon = Icons.Default.Notifications,
            isHome = true) },
    ) {
        padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        )
        {
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar()
            Spacer(modifier = Modifier.height(24.dp))
            MainImageCard(Globals.dummyNewsList.get(0))
            Spacer(modifier = Modifier.height(32.dp))
            NewsList("민주님을 위한 추천 뉴스", Globals.dummyNewsList)
            Spacer(modifier = Modifier.height(16.dp))
            NowTopNews("현재 TOP 경제 뉴스", Globals.dummyNewsList)
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

