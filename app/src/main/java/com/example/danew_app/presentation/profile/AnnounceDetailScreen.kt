package com.example.danew.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.DanewColors
import com.example.danew_app.core.widget.LazyLoadingIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.AnnounceViewModel

@Composable
fun AnnounceDetailScreen(announceId:String, navController: NavHostController) {
    val announceViewModel: AnnounceViewModel = hiltViewModel()
    val announce = announceViewModel.announceMap[announceId]
    val isLoading = announceViewModel.isLoading
    val errorMessage = announceViewModel.errorMessage

    LaunchedEffect(Unit) {
        announceViewModel.getAnnounceList()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            if (announce != null) {
                MainTopAppBar(
                    navController = navController,
                    title = announce.title,
                    icon = Icons.Default.Settings,
                    isBackIcon = true,
                    isHome = false)
                {
                    navController.navigate("settings")
                }
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

                announce != null -> {
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    item {
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp),
                            horizontalAlignment = Alignment.Start
                        ){
                            Text(
                                text = announce.title,
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(announce.createdAt, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(color = MaterialTheme.colorScheme.surface)
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = announce.content,
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontSize = 16.sp,
                                lineHeight = 26.sp,
                            )
                        }
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }

                else -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(padding),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("공지사항이 없습니다",
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            }
        }
    }
}