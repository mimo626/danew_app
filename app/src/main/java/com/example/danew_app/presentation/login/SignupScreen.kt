import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.MainTopAppBar

@Composable
fun SignupScreen(navHostController: NavHostController) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            MainTopAppBar(navController = navHostController, title = "", isBackIcon = true)
        },
        bottomBar = {
            // 완료 버튼
            BottomButton(text = "다음") {
                navHostController.navigate("signupAdd")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(Modifier.height(32.dp))

            Text("회원 정보 입력", fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.weight(1f))

            // 아이디 입력
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("아이디") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // 비밀번호 입력
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // 비밀번호 확인 입력
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호 확인") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
