package com.example.danew_app.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.danew_app.core.theme.ColorsLight

//검색바
@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("검색어를 입력해 주세요.") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "검색")
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = ColorsLight.lightGrayColor,    // 포커스된 상태의 배경색
            unfocusedContainerColor = ColorsLight.lightGrayColor,  // 포커스되지 않은 상태의 배경색
            disabledContainerColor = ColorsLight.lightGrayColor,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}