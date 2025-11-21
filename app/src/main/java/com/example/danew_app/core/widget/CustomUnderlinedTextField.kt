package com.example.danew_app.core.widget
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danew_app.R
import com.example.danew_app.core.theme.ColorsLight

// import com.example.danew_app.core.theme.ColorsLight // (ColorsLight 임포트)


@Composable
fun CustomUnderlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
    isPassword: Boolean = false,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontSize = 16.sp
        ),
        label = {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = ColorsLight.grayColor
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )
        },
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = ColorsLight.blueColor,
            unfocusedIndicatorColor = ColorsLight.lightGrayColor,
        ),

        visualTransformation = if (isPassword && !isPasswordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },

        trailingIcon = {
            if (isPassword) {
                // 1. painterResource를 사용하여 이미지 불러오기
                val iconPainter = if (isPasswordVisible) {
                    // 비밀번호가 보일 때 -> '눈' 모양 (eye.png)
                    painterResource(id = R.drawable.eye)
                } else {
                    // 비밀번호가 숨겨졌을 때 -> '눈 감은' 모양 (eye_hide.png)
                    painterResource(id = R.drawable.eye_hide)
                }

                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        // 2. imageVector 대신 painter 속성 사용
                        painter = iconPainter,
                        contentDescription = "비밀번호 보기/숨기기",
                        tint = ColorsLight.darkGrayColor,
                        modifier = Modifier.padding(top = 8.dp).size(24.dp)
                    )
                }
            }
        }
    )
}