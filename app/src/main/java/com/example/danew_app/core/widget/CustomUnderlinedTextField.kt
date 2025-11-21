package com.example.danew_app.core.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent // 중요!
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danew_app.R
import com.example.danew_app.core.theme.ColorsLight

@Composable
fun CustomUnderlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isCapsLockOn by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 16.sp),
            label = {
                Text(
                    text = label,
                    style = TextStyle(fontSize = 14.sp, color = ColorsLight.grayColor),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            },
            // [핵심 수정 부분]
            modifier = Modifier
                .fillMaxWidth()
                .onPreviewKeyEvent { event ->
                    // 1. Caps Lock 감지 (키 눌림/뗌 상관없이 이벤트 발생 시 체크)
                    isCapsLockOn = event.nativeKeyEvent.isCapsLockOn

                    // 2. 물리 키보드 Tab / Enter 처리
                    if (event.type == KeyEventType.KeyDown) {
                        if (event.key == Key.Tab || event.key == Key.Enter) {
                            val baseScope = object : KeyboardActionScope {
                                override fun defaultKeyboardAction(imeAction: ImeAction) {
                                }
                            }
                            // 부모가 넘겨준 imeAction 설정에 따라 적절한 행동 수행
                            when (keyboardOptions.imeAction) {
                                ImeAction.Next -> {
                                    keyboardActions.onNext?.invoke(baseScope)
                                    return@onPreviewKeyEvent true // 이벤트 소비 (탭 입력 방지)
                                }
                                ImeAction.Done -> {
                                    keyboardActions.onDone?.invoke(baseScope)
                                    return@onPreviewKeyEvent true // 이벤트 소비 (엔터 줄바꿈 방지)
                                }
                                else -> false
                            }
                        } else {
                            false
                        }
                    } else {
                        false
                    }
                },
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
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = {
                if (isPassword) {
                    val iconPainter = if (isPasswordVisible) {
                        painterResource(id = R.drawable.eye)
                    } else {
                        painterResource(id = R.drawable.eye_hide)
                    }

                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = iconPainter,
                            contentDescription = "비밀번호 보기/숨기기",
                            tint = ColorsLight.darkGrayColor,
                            modifier = Modifier.padding(top = 8.dp).size(24.dp)
                        )
                    }
                }
            }
        )

        if (isCapsLockOn) {
            Text(
                text = "Caps Lock이 켜져 있습니다.",
                color = ColorsLight.primaryColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}