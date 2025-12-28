import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.danew_app.core.theme.DanewColors
import com.example.danew_app.core.widget.CustomLoadingIndicator
import com.example.danew_app.core.widget.LazyLoadingIndicator
import com.example.danew_app.data.entity.NewsDetailType
import com.example.danew_app.data.mapper.toDomain
import com.example.danew_app.presentation.viewmodel.NewsViewModel
import com.example.danew_app.presentation.home.NewsItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchResultScreen(query:String, navHostController: NavHostController) {
    val viewModel: NewsViewModel = hiltViewModel()
    // 1. 사용자가 텍스트 필드에 입력 중인 값
    var searchQuery by remember { mutableStateOf(query) }

    // 2. 실제로 검색 아이콘을 눌러서 완료된 검색어 (결과 메시지용)
    var lastSearchedQuery by remember { mutableStateOf(query) }
    val listState = rememberLazyListState()
    val newsPagingItems = viewModel.newsBySearchQuery.collectAsLazyPagingItems()

    // 검색 실행 로직을 함수로 분리
    val performSearch = {
        if (searchQuery.isNotBlank()) {
            lastSearchedQuery = searchQuery // 화면에 표시될 '검색어' 업데이트
            viewModel.fetchNewsBySearchQuery(searchQuery)
            newsPagingItems.refresh()
        }
    }
    // 초기 검색 및 검색어 변경 시 로드 유도
    LaunchedEffect(query) {
        performSearch()
    }
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
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
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(16.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary
                        ),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            innerTextField()
                        },
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Default.Search, contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable{
                            performSearch()
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
                HorizontalDivider(thickness = 6.dp, color = MaterialTheme.colorScheme.secondary,
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("검색 결과", fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 20.dp),)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 초기 로딩 중일 때 로딩 인디케이터 표시
            if (newsPagingItems.loadState.refresh is LoadState.Loading) {
                item {
                    LazyLoadingIndicator()
                }
            }

            if(newsPagingItems.itemCount <= 0) {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(padding)
                            .padding(horizontal = 20.dp)
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("\'${lastSearchedQuery}\'에 대한 검색 결과가 없습니다.",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("단어의 철자가 정확한지 확인해주세요.",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // PagingData items 렌더링
            items(newsPagingItems.itemCount) { index ->
                val item = newsPagingItems[index]
                if (item != null) {
                    NewsItem(item.toDomain(), onItemClick = {
                        navHostController.navigate("details/${NewsDetailType.SEARCH}/${item.toDomain().newsId}")
                    })
                }
            }

            // 추가 로딩/에러 처리
            newsPagingItems.apply {
                when (loadState.append) {
                    is LoadState.Loading -> item {
                        CustomLoadingIndicator()
                    }
                    is LoadState.Error -> item { Text("오류") }
                    else -> {}
                }
            }
        }
    }
}
