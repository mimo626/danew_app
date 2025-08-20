import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import com.example.danew_app.presentation.home.NewsItem
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun SearchResultScreen(query:String, navHostController: NavHostController) {
    val viewModel: NewsViewModel = hiltViewModel()
    var searchQuery by remember { mutableStateOf(query) }
    val listState = rememberLazyListState()
    val newsList = viewModel.newsListBySearchQuery
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    // LaunchedEffect 안에서 실행 해야 query 값이 바뀌지 않는 이상 처음에 재실행 X
    LaunchedEffect(query) {
        viewModel.fetchNewsBySearchQuery(searchQuery)
    }

    Scaffold (
        containerColor = ColorsLight.whiteColor,
        topBar = {
            // 검색창
            Row (
                modifier = Modifier
                    .fillMaxWidth()
            ){
                IconButton(
                    onClick = { navHostController.popBackStack() },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "뒤로가기"
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ColorsLight.lightGrayColor, RoundedCornerShape(16.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            innerTextField()
                        },
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray,
                        modifier = Modifier.clickable{
                            viewModel.resetSearchResults()
                            viewModel.fetchNewsBySearchQuery(searchQuery)
                        }
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = listState
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
                HorizontalDivider(thickness = 6.dp, color = ColorsLight.lightGrayColor)

                Spacer(modifier = Modifier.height(16.dp))
                Row (
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text("뉴스", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "334",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = ColorsLight.grayColor
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            // 로딩
            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = ColorsLight.grayColor)
                    }
                }
            }

            // 에러 메시지
            if (errorMessage != null) {
                item {
                    Text("오류: $errorMessage", color = Color.Red)
                }
            }

            if(!isLoading && newsList.isEmpty()){
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("\"${searchQuery}\"에 대한 검색 결과가 없습니다.",
                            color = ColorsLight.darkGrayColor
                        )
                    }
                }
            }
            // 일반 뉴스 리스트
            items(items = newsList) { news ->
                NewsItem(news, navHostController)
            }
        }

        LaunchedEffect(listState) {
            snapshotFlow {
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            }
                .distinctUntilChanged() // 같은 인덱스면 중복 호출 방지
                .collect { lastVisibleIndex ->
                    val shouldLoadMore = lastVisibleIndex != null &&
                            lastVisibleIndex >= newsList.lastIndex - 2 &&
                            !isLoading

                    if (shouldLoadMore) {
                        Log.d("NewsViewModel", "lastVisibleIndex: ${lastVisibleIndex}")
                        Log.d("NewsViewModel", "newsList.lastIndex: ${newsList.lastIndex}")

                        viewModel.fetchNewsByCategory(searchQuery, loadMore = true)
                    }
                }
        }
    }
}
