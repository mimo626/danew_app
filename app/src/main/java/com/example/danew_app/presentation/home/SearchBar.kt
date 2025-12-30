package com.example.danew_app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

//검색바
@Composable
fun SearchBar(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable {
                navHostController.navigate("search")
            }
            .background(
                MaterialTheme.colorScheme.surface
                , RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row( modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,)
        {
            Text("검색어를 입력해 주세요.",
                color = MaterialTheme.colorScheme.onSurface

            )
            Icon(Icons.Default.Search, contentDescription = "검색",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}