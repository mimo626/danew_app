package com.example.danew_app.core.widget
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KeywordGrid(
    allKeywords: List<String>,
    selectedKeywords: MutableList<String>, // 상태 변경을 위해 MutableList 사용
    maxSelection: Int = 5,
    onMaxReached: () -> Unit = {}, // 최대치 도달 시 실행할 액션 (스낵바 등)
    modifier: Modifier = Modifier
) {
    // 내부 클릭 로직 통합
    val handleKeywordClick = { keyword: String ->
        if (selectedKeywords.contains(keyword)) {
            selectedKeywords.remove(keyword)
        } else {
            if (selectedKeywords.size < maxSelection) {
                selectedKeywords.add(keyword)
            } else {
                onMaxReached() // 외부(부모)에 알림
            }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(allKeywords) { keyword ->
            val isSelected = selectedKeywords.contains(keyword)

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surface
                    )
                    .clickable { handleKeywordClick(keyword) } // 통합된 로직 호출
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = keyword,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}