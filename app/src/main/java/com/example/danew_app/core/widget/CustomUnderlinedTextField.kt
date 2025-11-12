package com.example.danew_app.core.widget
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            if (isPassword) { // isPassword 플래그가 true일 때만 아이콘 표시
                val icon = if (isPasswordVisible) {
                    // 비밀번호가 보일 때 -> '숨기기' 아이콘 (VisibilityOff)
                    Icons.Filled.Face
                } else {
                    // 비밀번호가 숨겨졌을 때 -> '보이기' 아이콘 (Visibility)
                    Icons.Default.Face
                }

                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "비밀번호 보기/숨기기",
                    )
                }
            }
        }
    )
}