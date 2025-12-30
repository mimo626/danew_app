package com.example.danew.presentation.home
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.danew_app.core.widget.BottomButton

@Composable
fun StartScreen(navHostController: NavHostController,) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Column {
                BottomButton(text = "로그인") {
                    navHostController.navigate("login")
                }
                BottomButton(text = "회원가입",
                    backgroundColor = MaterialTheme.colorScheme.onSurface) {
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
                color = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally) // Column 안에서는 가로 정렬
            )
        }
    }
}

