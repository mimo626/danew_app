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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.danew_app.core.theme.ColorsLight
import com.example.danew_app.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(navHostController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    val viewModel: SearchViewModel = hiltViewModel()
    val recentSearches by viewModel.recentSearches.collectAsState()

    val focusRequester = remember { FocusRequester() }

    // 화면 진입 시 포커스 요청
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
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
                            if (searchQuery.isEmpty()) {
                                Text("검색어를 입력해 주세요.", color = ColorsLight.grayColor)
                            }
                            innerTextField()
                        },
                        modifier = Modifier.weight(1f).focusRequester(focusRequester)
                    )
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray,
                        modifier = Modifier.clickable{
                            viewModel.saveSearch(searchQuery)
                            navHostController.navigate("search/${searchQuery}")
                        }
                    )
                }
            }
        }
        ){
        padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            // 최근 검색어 타이틀
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("최근 검색어", fontWeight = FontWeight.Bold)
                Text(
                    "전체 삭제",
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        viewModel.clearAll()
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 최근 검색어 목록
            LazyColumn {
                items(recentSearches) { history ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(history.keyword,
                            modifier = Modifier
                                .weight(1f)
                                .clickable{
                                navHostController.navigate("search/${history.keyword}")
                            })
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "삭제",
                            tint = Color.Gray,
                            modifier = Modifier.clickable {
                                viewModel.deleteSearch(history.keyword)
                            }
                        )
                    }
                }
            }
        }
    }
}
