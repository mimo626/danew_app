package com.example.danew.presentation.home
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.core.widget.MainTopAppBar

@Composable
fun AlarmScreen(navHostController: NavHostController,) {

    val alarmList = listOf(
        "알람1", "알람2", "알람3", "알람4"
    )
    Scaffold(
        containerColor = ColorsLight.whiteColor,
        topBar = {
            MainTopAppBar( navController = navHostController,
                title = "알림",
                isBackIcon = true) },
    ) {
            padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
        )
        {
            Spacer(modifier = Modifier.height(16.dp))
            alarmList.forEach {
                Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                        }
                    )
                {
                    Text(
                        it, maxLines = 2, fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 20.dp).padding(top=16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "3시간 전",fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = ColorsLight.grayColor,
                        modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = 16.dp)
                    )
                    HorizontalDivider(color = ColorsLight.lightGrayColor)
                }
            }

        }
    }
}

