package com.example.danew.presentation.home
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.danew_app.core.gloabals.Globals
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.home.MainImageCard
import com.example.danew_app.presentation.home.NewsList
import com.example.danew_app.presentation.home.NowTopNews
import com.example.danew_app.presentation.home.SearchBar

@Composable
fun HomeScreen(navController: NavHostController) {
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
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item { SearchBar(navHostController = navController) }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item { MainImageCard(Globals.dummyNewsList[0], navController) }

            item { Spacer(modifier = Modifier.height(28.dp)) }

            item { NewsList("민주님을 위한 추천 뉴스", Globals.dummyNewsList, navController) }

            item { Spacer(modifier = Modifier.height(28.dp)) }

            item { NowTopNews("현재 TOP 경제 뉴스", Globals.dummyNewsList, navController) }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}


