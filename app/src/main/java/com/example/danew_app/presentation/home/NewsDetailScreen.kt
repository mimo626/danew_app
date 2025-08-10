import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.danew_app.domain.model.NewsModel

@Composable
fun NewsDetailScreen(news: NewsModel, onBackClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        // 상단 뒤로가기
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "뒤로가기"
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 카테고리 태그
        news.category?.firstOrNull()?.let { category ->
            Box(
                modifier = Modifier
                    .background(Color(0xFFE8F3E8), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(category, color = Color(0xFF3C5E3C), fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 뉴스 제목
        Text(
            text = news.title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        // 날짜
        Text(news.pubDate, fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))

        // 작성자
        news.creator?.firstOrNull()?.let { creator ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("작성자", fontSize = 10.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(creator, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 본문 (AI 뉴스 요약)
        Text("AI 뉴스 요약본", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(news.description, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // 뉴스 원문 보기
        Text(
            text = "뉴스 원문 보기",
            color = Color.Blue,
            fontSize = 14.sp,
            modifier = Modifier.clickable {
                // TODO: 뉴스 원문 링크 열기
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 이미지
        news.imageUrl?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "뉴스 이미지",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
