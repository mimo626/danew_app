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
fun LoginScreen(navHostController: NavHostController) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            MainTopAppBar(navController = navHostController, title = "로그인", isBackIcon = true)
        },
        bottomBar = {
            // 완료 버튼
            BottomButton(text = "로그인하기") {
                navHostController.navigate("home")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {

            Spacer(modifier = Modifier.weight(1f))

            // 아이디 입력
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("아이디") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            )

            Spacer(Modifier.height(16.dp))

            // 비밀번호 입력
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
