package com.example.danew_app.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.danew_app.core.theme.DanewColors

@Composable
fun SplashScreen() {

    Scaffold(
        containerColor = DanewColors.whiteColor,
    ) {
            padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center
        )
        {
            Text("DANEW", style = TextStyle(fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = DanewColors.primaryColor,),
                modifier = Modifier.align(Alignment.CenterHorizontally) // Column 안에서는 가로 정렬
            )
        }
    }
}
