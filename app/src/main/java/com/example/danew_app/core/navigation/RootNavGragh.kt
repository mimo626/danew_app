import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.danew.presentation.diary.DiaryWriteScreen

@Composable
fun RootNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "main",
        modifier = modifier,
    ) {
        // 메인 탭 네비게이션
        composable("main") {
            MainScreen(rootNavController = navController) // 내부에서 BottomNavGraph 호출
        }

        // BottomNav 없는 화면들
        composable("details/{newsId}") { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId")
            newsId?.let { NewsDetailScreen(it, navController) }
        }

        composable("diary/{date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            date?.let { DiaryWriteScreen(it, navController) }
        }
    }
}
