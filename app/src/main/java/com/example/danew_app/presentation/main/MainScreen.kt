import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.danew.core.componet.BottomNavBar
import com.example.danew.core.navigation.BottomNavGraph

@Composable
fun MainScreen(rootNavController: NavHostController)
{
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(bottomNavController) }
    ) { innerPadding ->
        BottomNavGraph(bottomNavController = bottomNavController,
            rootNavController = rootNavController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}