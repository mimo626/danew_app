import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.danew.core.componet.BottomNavBar
import com.example.danew.core.navigation.BottomNavGraph
import com.example.danew.core.navigation.BottomNavItem

@Composable
fun MainScreen(navHostController: NavHostController)
{
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = { if (currentRoute in listOf(
                BottomNavItem.Home.route,
                BottomNavItem.Category.route,
                BottomNavItem.Diary.route,
                BottomNavItem.Bookmark.route,
                BottomNavItem.My.route
            )
        ) {
            BottomNavBar(navHostController)
        } }
    ) { innerPadding ->
        BottomNavGraph(navHostController = navHostController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}