package com.example.danew.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.LazyLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.profile.MyPageMenuItem
import com.example.danew_app.presentation.viewmodel.AnnounceViewModel

@Composable
fun AnnounceScreen(navController: NavHostController) {
    val announceViewModel: AnnounceViewModel = hiltViewModel()
    val announceList = announceViewModel.announceList
    val isLoading = announceViewModel.isLoading
    val errorMessage = announceViewModel.errorMessage

    LaunchedEffect(Unit) {
        announceViewModel.getAnnounceList()
    }

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar(
                navController = navController,
                title = "공지사항",
                icon = Icons.Default.Settings,
                isBackIcon = true,
                isHome = false)
            {
                navController.navigate("settings")
            }
        },
        bottomBar = {
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                isLoading -> {
                    item {
                        LazyLoadingIndicator(padding)
                    }
                }

                announceList.isNotEmpty() -> {
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    items(announceList) {
                        announce ->
                        MyPageMenuItem(announce.title) {

                        }
                    }
                    item { Spacer(modifier = Modifier.height(28.dp)) }
                }

                else -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(padding),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("공지사항이 없습니다")
                        }
                    }
                }
            }
        }
    }
}