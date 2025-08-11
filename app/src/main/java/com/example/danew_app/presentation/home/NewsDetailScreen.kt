import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.presentation.category.NewsViewModel
import com.example.danew_app.presentation.home.MainTopAppBar

@Composable
fun NewsDetailScreen(newsId: String, navHostController: NavHostController) {
    val viewModel: NewsViewModel = hiltViewModel()
    val newsList by viewModel.newsListById.collectAsState()
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    LaunchedEffect(newsId) {
        viewModel.fetchNewsById(id = newsId)
    }

    Scaffold (
        topBar = {
            MainTopAppBar(navController = navHostController, title = "", icon = null,
                isHome = false, isBackIcon = true)
        }
    ){
        padding ->
        Column(
            modifier = Modifier.padding(padding).padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())

        ) {
            Spacer(modifier = Modifier.height(8.dp))

            when {
                isLoading ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = ColorsLight.grayColor
                        )
                    }
                errorMessage != null -> {
                    // 에러 메시지
                    errorMessage?.let {
                        Text("오류: $it", color = Color.Red)
                    }
                }
                newsList.isNotEmpty() -> {
                    // 카테고리 태그
                    newsList.get(0).category?.firstOrNull()?.let { category ->
                        Box(
                            modifier = Modifier
                                .background(ColorsLight.secondaryColor, shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(category, color = ColorsLight.primaryColor, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 뉴스 제목
                    Text(
                        text = newsList.get(0).title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 날짜
                    Text(newsList.get(0).pubDate, fontSize = 12.sp, color = ColorsLight.grayColor)

                    Spacer(modifier = Modifier.height(8.dp))

                    // 작성자
                    newsList.get(0).creator?.firstOrNull()?.let { creator ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .background(ColorsLight.lightGrayColor, shape = RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("작성자", fontSize = 12.sp, color = ColorsLight.grayColor)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(creator, fontSize = 14.sp,  color = ColorsLight.grayColor)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row (
                        modifier = Modifier.align(Alignment.End)
                    ){
                        IconButton(onClick = {}) {
                            Icon(
                                tint = ColorsLight.darkGrayColor,
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "북마크"
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(onClick = {}) {
                            Icon(
                                tint = ColorsLight.darkGrayColor,
                                imageVector = Icons.Default.Share,
                                contentDescription = "공유"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("AI 뉴스 요약본", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(newsList.get(0).description, fontSize = 14.sp)
                        // 뉴스 원문 보기
                        Text(
                            text = "뉴스 원문 보기",
                            color = ColorsLight.grayColor,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable {
                                // TODO: 뉴스 원문 링크 열기
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 이미지
                    newsList.get(0).imageUrl?.let { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "뉴스 이미지",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            "뉴스 데이터가 없습니다.",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
