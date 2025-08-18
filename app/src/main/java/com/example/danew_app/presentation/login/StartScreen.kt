package com.example.danew.presentation.home
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.danew_app.core.gloabals.Globals
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.home.MainImageCard
import com.example.danew_app.presentation.home.NewsList
import com.example.danew_app.presentation.home.NowTopNews
import com.example.danew_app.presentation.home.SearchBar

@Composable
fun StartScreen(navHostController: NavHostController,) {

    Scaffold(
        containerColor = ColorsLight.whiteColor,
        bottomBar = {
            Column {
                BottomButton(text = "로그인하기") {
                    navHostController.navigate("signin")
                }
                BottomButton(text = "가입하기",
                    backgroundColor = ColorsLight.grayColor) {
                    navHostController.navigate("signup")
                }
            }
        }
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
                color = ColorsLight.primaryColor,),
                modifier = Modifier.align(Alignment.CenterHorizontally) // Column 안에서는 가로 정렬
            )
        }
    }
}

