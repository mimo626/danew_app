import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.widget.BottomButton
import com.example.danew_app.core.widget.CustomLinearProgressIndicator
import com.example.danew_app.core.widget.MainTopAppBar
import com.example.danew_app.presentation.viewmodel.SignupViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun SignupScreen(navHostController: NavHostController, viewModel: SignupViewModel) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isIdChecked by remember { mutableStateOf(false) } // 중복 체크 버튼 눌렀는지 여부

    val parentEntry = navHostController.getBackStackEntry("signupFlow")
    val viewModel: SignupViewModel = hiltViewModel(parentEntry)

    // ViewModel 상태 관찰
    val isUserIdAvailable = viewModel.isUserIdAvailable

    // 버튼 활성화 조건
    val isNextEnabled = id.isNotBlank() &&
            password.isNotBlank() &&
            confirmPassword.isNotBlank() &&
            password == confirmPassword &&
            isIdChecked &&
            isUserIdAvailable == true

    Scaffold(
        topBar = {
            MainTopAppBar(navController = navHostController, title = "", isBackIcon = true)
        },
        bottomBar = {
            BottomButton(
                text = "다음",
                isEnabled = isNextEnabled // ✅ 조건 만족 시 활성화
            ) {
                viewModel.updateUserId(id)
                viewModel.updatePassword(password)
                navHostController.navigate("signupAdd")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            CustomLinearProgressIndicator(1 / 4f)
            Spacer(Modifier.height(32.dp))

            Text(
                "회원 정보 입력",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            // 아이디 입력
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = id,
                    onValueChange = {
                        id = it
                        isIdChecked = false // 아이디 변경되면 다시 체크해야 함
                    },
                    label = { Text("아이디") },
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    viewModel.checkUserId(id)
                    isIdChecked = true
                }
                ) {
                    Text("중복확인")
                }
            }

            // 중복 확인 결과 표시
            when (viewModel.isUserIdAvailable) {
                true -> Text(
                    "사용 가능한 아이디입니다.",
                    color = Color.Green,
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                )
                false -> Text(
                    "이미 사용 중인 아이디입니다.",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                )
                else -> {}
            }

            Spacer(Modifier.height(16.dp))

            // 비밀번호 입력
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(16.dp))

            // 비밀번호 확인 입력
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("비밀번호 확인") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            // 비밀번호 불일치 시 안내
            if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                Text(
                    "비밀번호가 일치하지 않습니다.",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
